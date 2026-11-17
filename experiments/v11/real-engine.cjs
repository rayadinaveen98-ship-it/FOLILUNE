const fs=require('fs'),http=require('http'),JSZip=require('jszip'),{chromium}=require('playwright-core');
const results=[],check=(name,pass,detail='')=>{results.push({name,pass:!!pass,detail});console.log((pass?'PASS ':'FAIL ')+name+': '+detail)};
const xml=s=>'<?xml version="1.0" encoding="UTF-8"?>'+s;
async function test(){let browser,server;try{
const z=new JSZip();z.file('mimetype','application/epub+zip',{compression:'STORE'});
z.file('META-INF/container.xml',xml('<container xmlns="urn:oasis:names:tc:opendocument:xmlns:container" version="1.0"><rootfiles><rootfile full-path="OEBPS/book.opf" media-type="application/oebps-package+xml"/></rootfiles></container>'));
z.file('OEBPS/book.opf',xml('<package xmlns="http://www.idpf.org/2007/opf" version="3.0" unique-identifier="id"><metadata xmlns:dc="http://purl.org/dc/elements/1.1/"><dc:identifier id="id">folilune-v11</dc:identifier><dc:title>FOLILUNE Original Test</dc:title><dc:creator>FOLILUNE</dc:creator><dc:language>en</dc:language><meta property="dcterms:modified">2026-11-11T00:00:00Z</meta></metadata><manifest><item id="n" href="nav.xhtml" media-type="application/xhtml+xml" properties="nav"/><item id="a" href="a.xhtml" media-type="application/xhtml+xml"/><item id="b" href="b.xhtml" media-type="application/xhtml+xml"/><item id="i" href="art.png" media-type="image/png"/></manifest><spine><itemref idref="a"/><itemref idref="b"/></spine></package>'));
z.file('OEBPS/nav.xhtml',xml('<html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops"><head><title>Contents</title></head><body><nav epub:type="toc"><ol><li><a href="a.xhtml">Chapter One</a></li><li><a href="b.xhtml">Chapter Two</a></li></ol></nav></body></html>'));
z.file('OEBPS/a.xhtml',xml('<html xmlns="http://www.w3.org/1999/xhtml"><head><title>Chapter One</title></head><body><h1>Chapter One</h1><p>Original FOLILUNE reader-engine test prose.</p><p><em>Preserve expressive writing.</em></p><img src="art.png" alt="Original test illustration"/></body></html>'));
z.file('OEBPS/b.xhtml',xml('<html xmlns="http://www.w3.org/1999/xhtml"><head><title>Chapter Two</title></head><body><h1>Chapter Two</h1><p>A second original chapter for navigation verification.</p></body></html>'));
z.file('OEBPS/art.png',Buffer.from('iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+/LXYAAAAASUVORK5CYII=','base64'));
const bytes=await z.generateAsync({type:'nodebuffer',compression:'DEFLATE'});
server=http.createServer((req,res)=>{res.writeHead(200,{'content-type':'text/html'});res.end('<html><body><div id="viewer" style="width:900px;height:700px"></div></body></html>')});await new Promise(r=>server.listen(0,'127.0.0.1',r));
browser=await chromium.launch({executablePath:process.env.CHROME_PATH||'/usr/bin/google-chrome',headless:true,args:['--no-sandbox','--disable-dev-shm-usage']});
const page=await browser.newPage({viewport:{width:1200,height:800}}),errors=[];page.on('pageerror',e=>errors.push(e.message));await page.goto('http://127.0.0.1:'+server.address().port);
await page.addScriptTag({path:require.resolve('jszip/dist/jszip.min.js')});await page.addScriptTag({path:require.resolve('epubjs/dist/epub.min.js')});
check('Unmocked engine loaded',await page.evaluate(()=>typeof ePub==='function'&&typeof JSZip==='function'),'npm distributions');
const md=await page.evaluate(async b64=>{
const buf=Uint8Array.from(atob(b64),c=>c.charCodeAt(0));window.book=ePub(buf.buffer);await book.ready;const meta=await book.loaded.metadata,nav=await book.loaded.navigation;
window.reader=book.renderTo('viewer',{width:'100%',height:'100%',flow:'paginated',spread:'none',allowScriptedContent:false});await reader.display();
return {title:meta.title,toc:nav.toc};
},bytes.toString('base64'));
check('Original publication metadata',md.title==='FOLILUNE Original Test',md.title);check('Two TOC entries',md.toc.length===2,String(md.toc.length));
await page.waitForTimeout(650);
const first=await page.evaluate(()=>{const d=document.querySelector('#viewer iframe')?.contentDocument;return {text:d?.body?.innerText||'',images:[...(d?.images||[])].map(i=>i.complete&&i.naturalWidth>0),cfi:reader.currentLocation()?.start?.cfi||''}});
check('Actual chapter-one rendering',first.text.includes('Chapter One')&&first.text.includes('Original FOLILUNE'),first.text.slice(0,80));
check('Embedded illustration displayed',first.images.includes(true),JSON.stringify(first.images));
check('Reading CFI available',first.cfi.startsWith('epubcfi('),first.cfi.slice(0,80));
fs.mkdirSync('experiments/v11/results',{recursive:true});await page.screenshot({path:'experiments/v11/results/actual-render.png',fullPage:true});
await page.evaluate(href=>reader.display(href),md.toc[1].href);await page.waitForTimeout(300);
const second=await page.evaluate(()=>document.querySelector('#viewer iframe')?.contentDocument?.body?.innerText||'');
check('Actual chapter-two navigation',second.includes('Chapter Two'),second.slice(0,80));
if(first.cfi){await page.evaluate(cfi=>reader.display(cfi),first.cfi);await page.waitForTimeout(300);const restored=await page.evaluate(()=>document.querySelector('#viewer iframe')?.contentDocument?.body?.innerText||'');check('Saved CFI restores chapter',restored.includes('Chapter One'),restored.slice(0,80));}
check('No uncaught browser exceptions',errors.length===0,errors.join('; ')||'none');
}catch(e){check('Engine test executed',false,e.stack||String(e))}
finally{if(browser)await browser.close().catch(()=>{});if(server)await new Promise(r=>server.close(r));fs.mkdirSync('experiments/v11/results',{recursive:true});fs.writeFileSync('experiments/v11/results/report.json',JSON.stringify({engine:'epubjs@0.3.93',mode:'REAL ENGINE — no mocks',checks:results},null,2));if(results.some(x=>!x.pass))process.exitCode=1;}}
test().catch(e=>{console.error(e);process.exitCode=1});
