/* FOLILUNE G2-B: connected Collections, Notes and Help.
   Never invent book content or replace the real EPUB renderer. */
(function(){
 'use strict';
 const api=window.FOLILUNE_REAL_UI;
 if(!api||api.version!=='g2-bridge-1')return;
 const esc=v=>String(v??'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
 const ui={shelf:'local',noteFilter:'all',noteQuery:'',helpQuery:''};
 const head=(eyebrow,title,subtitle)=>'<div class="g2-heading"><div><div class="eyebrow">'+esc(eyebrow)+'</div><h1 class="page-title">'+esc(title)+'</h1><p class="page-sub">'+esc(subtitle)+'</p></div></div>';
 const card=(b)=>'<button class="g2b-book" data-book="'+esc(b.id)+'" aria-label="Open '+esc(b.title)+'">'+
   '<span class="g2b-book-cover '+(['forest','coral','midnight','sage','violet','sand','cream'].includes(b.cover)?b.cover:'sage')+'"><span class="g2b-moon">☾</span><strong>'+esc(b.title)+'</strong><small>'+esc(b.author)+'</small></span>'+
   '<span class="g2b-book-title">'+esc(b.title)+'</span><span class="g2b-book-caption">'+(b.imported?'On this device':'Interactive sample / showcase')+'</span></button>';
 const shelves=[
  {id:'local',title:'My library',description:'Every book imported on this device',symbol:'☷',accent:'moss',filter:b=>b.imported},
  {id:'reading',title:'Currently reading',description:'Local books you have opened',symbol:'◒',accent:'brass',filter:b=>b.imported&&Boolean(b.lastOpened||b.progress>0)},
  {id:'unopened',title:'Waiting to be opened',description:'Imported books you have not yet started',symbol:'✧',accent:'sand',filter:b=>b.imported&&!b.lastOpened&&!(b.progress>0)},
  {id:'samples',title:'Experience samples',description:'Fictional examples, separate from your library',symbol:'◇',accent:'slate',filter:b=>!b.imported}
 ];
 function collections(){
  const s=api.snapshot(),all=s.books||[];
  const def=shelves.find(x=>x.id===ui.shelf)||shelves[0];
  const selected=all.filter(def.filter);
  return '<div class="content g2-content g2b-content">'+head('YOUR READING SHELVES','Every world has its place.','These are automatic views of real local books. Custom collection creation is a later feature.')+
    '<section class="g2b-collection-grid" aria-label="Reading shelves">'+shelves.map(x=>{
     const num=all.filter(x.filter).length;
     return '<button class="g2b-shelf g2b-'+x.accent+(ui.shelf===x.id?' active':'')+'" data-g2b-shelf="'+x.id+'" aria-pressed="'+(ui.shelf===x.id)+'">'+
      '<span class="g2b-shelf-symbol" aria-hidden="true">'+x.symbol+'</span><strong>'+esc(x.title)+'</strong><span>'+esc(x.description)+'</span><small>'+num+' '+(num===1?'book':'books')+'</small></button>';
    }).join('')+'</section>'+
    '<section class="g2-section"><div class="section-title"><h2>'+esc(def.title)+'</h2><span class="g2-honest">'+selected.length+' '+(selected.length===1?'title':'titles')+'</span></div>'+
    (selected.length?'<div class="g2b-book-grid">'+selected.map(card).join('')+'</div>':
    '<div class="g2-empty"><strong>This shelf is empty.</strong><p>'+(ui.shelf==='samples'?'No samples are currently included.':ui.shelf==='reading'?'Open a local book and it will appear here.':ui.shelf==='unopened'?'You have already started your imported books.':'Add a DRM-free EPUB, TXT or Markdown book to fill this shelf.')+'</p>'+
    '<button class="secondary" '+(ui.shelf==='reading'?'data-view="library"':'data-action="import-file"')+'>'+(ui.shelf==='reading'?'Browse my library':'Add a book')+'</button></div>')+
    '</section><div class="g2-assurance"><span>ⓘ</span><div><strong>A clear distinction</strong><p>These shelves are generated from your existing local library. They are not editable custom collections, and sample works are not published catalog titles. Nothing is uploaded.</p></div></div></div>';
 }
 function annotations(){
  const s=api.snapshot();
  const notes=(s.notes||[]).map(n=>({...n,kind:'note'}));
  const highlights=(s.highlights||[]).map(n=>({...n,kind:'highlight'}));
  return [...notes,...highlights].sort((a,b)=>(Date.parse(b.at||'')||0)-(Date.parse(a.at||'')||0));
 }
 let visibleNotes=[];
 function noteRows(){
  const books=api.snapshot().books;
  visibleNotes=annotations().filter(n=>(ui.noteFilter==='all'||n.kind===ui.noteFilter)&&
    (n.quote+' '+n.text+' '+(books.find(b=>b.id===n.bookId)?.title||'')+' '+n.source).toLowerCase().includes(ui.noteQuery.trim().toLowerCase()));
  return visibleNotes.length?'<div class="g2b-note-list">'+visibleNotes.map((n,i)=>{
   const book=books.find(b=>b.id===n.bookId);
   const source=book?book.title:(n.source==='study'?'Study sample':'Source unavailable');
   const time=Number.isFinite(Date.parse(n.at||''))?new Date(n.at).toLocaleDateString():null;
   const main=n.text||n.quote||'(Empty note)';
   const hasLink=Boolean(book)||n.source==='study';
   return '<article class="g2b-note-card"><div class="g2b-note-overline"><span class="g2b-note-type">'+(n.kind==='note'?'✎ Note':'✦ Highlight')+'</span><span>'+esc(source)+(time?' · '+esc(time):'')+'</span></div>'+
   '<p class="g2b-note-text">'+esc(main)+'</p>'+(n.text&&n.quote?'<blockquote class="g2b-note-quote">'+esc(n.quote)+'</blockquote>':'')+
   '<div class="g2b-note-foot"><small>'+esc(book?(book.imported?'Imported book':'Example reader'):'Local note')+'</small>'+
   (hasLink?'<button class="secondary" data-g2b-open-note="'+i+'">'+(book?'Open source book':'Open study sample')+' ↗</button>':
   '<span class="g2-honest">Original source no longer available</span>')+'</div></article>';
  }).join('')+'</div>':
  '<div class="g2-empty"><strong>No matching reflections yet.</strong><p>Notes and highlights created in the text-preview reader or study example appear here. Full EPUB text highlights are not supported in this test.</p><button class="secondary" data-view="library">Open library</button></div>';
 }
 function notes(){
  const all=annotations();
  return '<div class="content g2-content g2b-content">'+head('YOUR READING JOURNAL','What stayed with you.','The real notes and highlights you have saved locally — never invented reflections.')+
    '<div class="g2b-note-header"><label class="g2b-query"><span aria-hidden="true">⌕</span><input id="g2bNoteQuery" type="search" value="'+esc(ui.noteQuery)+'" placeholder="Search your notes" aria-label="Search saved notes and highlights"></label>'+
    '<div class="g2b-note-filters" role="group" aria-label="Filter annotations">'+[['all','All'],['note','Notes'],['highlight','Highlights']].map(([k,name])=>
    '<button class="g2-choice'+(ui.noteFilter===k?' selected':'')+'" aria-pressed="'+(ui.noteFilter===k)+'" data-g2b-note-filter="'+k+'">'+name+'</button>').join('')+'</div></div>'+
    '<div class="g2b-note-count">'+all.length+' locally saved '+(all.length===1?'entry':'entries')+'</div>'+
    '<div id="g2bNotesResults">'+noteRows()+'</div>'+
    '<div class="g2-assurance"><span>⌑</span><div><strong>Current annotation support</strong><p>Notes and highlights come from the existing preview/study reader. Opening a source book resumes its saved position, not the exact highlighted sentence. EPUB.js CFI-linked highlights are planned for G3. Keep device data safe: cloud backup is not active.</p></div></div></div>';
 }
 const faq=[
  {q:'Which files can I import?',a:'This test accepts DRM-free EPUB, TXT and Markdown files. PDF and CBZ import are not implemented. EPUB fidelity varies by publisher file, layout and script.'},
  {q:'How do I add a book?',a:'Choose Add a book from Home or Library, then select a local document through the Android file picker. You need permission to read the source book.'},
  {q:'Where are my imported books stored?',a:'Imported files are stored in the app’s local Android WebView data, including IndexedDB where available. Clearing app data or uninstalling this debug build can erase those books and their saved positions.'},
  {q:'Do my bookmarks survive a restart?',a:'The emulator has passed selected EPUB CFI and bookmark recovery tests. Physical tablet tests, difficult EPUBs and migration between APK signatures are not yet certified.'},
  {q:'Can I sync with my Google account?',a:'Not yet. Google sign-in, automatic cloud backup and cross-device sync are not enabled. A future implementation will need explicit permission, security and recovery checks.'},
  {q:'Can I back up everything now?',a:'The Android debug app does not have a verified native export and restore workflow. The legacy page may show experimental backup options, but do not rely on them for original EPUB files. Keep your originals elsewhere.'},
  {q:'How do Notes and Saved Places differ?',a:'Saved Places contains EPUB bookmarks and preview chapter markers. Notes contains local annotations made in supported text previews or the Study sample. Full EPUB text-range highlights are not yet integrated.'},
  {q:'Are the sample books a live catalog?',a:'No. The examples demonstrate reading formats and visual styles. Some are non-opening cover placeholders; they are not published or licensed storefront books.'},
  {q:'Does FOLILUNE work offline?',a:'This APK includes the EPUB.js renderer locally and blocks remote HTTP requests. Books imported from device storage can be opened without an internet connection, subject to local storage and format limits.'},
  {q:'Can I read comics, manga or PDF files?',a:'The Comic and Study screens are interactive concept demonstrations. Real CBZ and PDF engines, accessible reading modes and licensed catalog delivery remain future milestones.'},
  {q:'What can I do if a book will not open?',a:'Check that the file is a valid DRM-free EPUB, TXT or Markdown. Keep the original file and avoid repeatedly uninstalling the app. Some EPUBs with complex formatting are outside the verified compatibility matrix.'}
 ];
 function renderFaqRows(){
  const q=ui.helpQuery.trim().toLowerCase();
  const list=faq.filter(x=>(x.q+' '+x.a).toLowerCase().includes(q));
  return list.length?list.map(x=>'<details class="g2b-faq"><summary>'+esc(x.q)+'</summary><p>'+esc(x.a)+'</p></details>').join(''):
    '<div class="g2-empty"><strong>No matching help article.</strong><p>Try a different word or browse the questions below.</p></div>';
 }
 function help(){
  return '<div class="content g2-content g2b-content">'+head('HERE WHEN YOU NEED IT','A little guidance goes a long way.','Direct answers about this particular Android test — no features promised as already shipped.')+
   '<label class="g2b-query g2b-help-query"><span aria-hidden="true">⌕</span><input id="g2bHelpQuery" type="search" value="'+esc(ui.helpQuery)+'" placeholder="Search help topics" aria-label="Search help topics"></label>'+
   '<div class="g2b-help-layout"><section aria-label="Frequently asked questions"><h2>Frequently asked questions</h2><div class="g2b-faq-list" id="g2bFaqList">'+renderFaqRows()+'</div></section>'+
   '<aside class="g2b-help-aside"><span class="g2b-help-symbol">☾</span><h2>Keep your worlds safe.</h2><p>FOLILUNE G2 is a private, experimental reader. Keep your original EPUBs and remember that different test app packages have separate local storage.</p>'+
   '<button class="secondary" data-view="library">My library ↗</button><button class="secondary" data-view="settings">Reading settings ↗</button>'+
   '<div class="g2b-shortcuts"><strong>Reader keyboard shortcuts</strong><small>Alt + 1–5: original Home, Library, Story, Comic, Study routes</small><small>Esc: close reader tools</small><small>Arrow keys: turn pages in applicable readers</small></div></aside></div></div>';
 }
 for(const [name,fn,title] of [['collections',collections,'Collections'],['notes',notes,'Notes & Highlights'],['help',help,'Help']])
  api.register(name,fn,title);
 const nav=document.querySelector('.g2-nav-extra');
 if(nav&&!nav.querySelector('[data-view="collections"]')){
  const button=(view,ico,label)=>'<button class="nav-btn" data-view="'+view+'"><span class="nav-ico" aria-hidden="true">'+ico+'</span><span class="nav-label">'+label+'</span></button>';
  nav.querySelector('[data-view="saved"]')?.insertAdjacentHTML('beforebegin',button('collections','▥','Collections'));
  nav.querySelector('[data-view="settings"]')?.insertAdjacentHTML('beforebegin',button('notes','✎','Notes'));
  nav.insertAdjacentHTML('beforeend',button('help','?','Help'));
 }
 const journal=document.querySelector('.sidebar [data-action="open-journal"]');
 if(journal){journal.removeAttribute('data-action');journal.dataset.view='notes';journal.querySelector('.nav-label').textContent='Reading journal';}
 const helpButton=document.querySelector('.topbar-right [data-action="help"]');
 if(helpButton){helpButton.removeAttribute('data-action');helpButton.dataset.view='help';helpButton.setAttribute('aria-label','Open help');helpButton.title='Help';}
 document.addEventListener('click',e=>{
  const el=e.target.closest('[data-g2b-shelf],[data-g2b-note-filter],[data-g2b-open-note]');
  if(!el)return;
  e.preventDefault();
  if(el.dataset.g2bShelf){ui.shelf=el.dataset.g2bShelf;api.navigate('collections');return;}
  if(el.dataset.g2bNoteFilter){ui.noteFilter=el.dataset.g2bNoteFilter;api.navigate('notes');return;}
  if(el.dataset.g2bOpenNote!==undefined){
    const item=visibleNotes[Number(el.dataset.g2bOpenNote)];
    if(item)api.openAnnotation(item.kind,item.index);
  }
 });
 document.addEventListener('input',e=>{
  if(e.target.id==='g2bNoteQuery'){
   ui.noteQuery=e.target.value;
   const target=document.getElementById('g2bNotesResults');
   if(target)target.innerHTML=noteRows();
  }
  if(e.target.id==='g2bHelpQuery'){
   ui.helpQuery=e.target.value;
   const target=document.getElementById('g2bFaqList');
   if(target)target.innerHTML=renderFaqRows();
  }
 });
 window.FOLILUNE_G2_SUPPORT=Object.freeze({version:'g2b',count:()=>api.snapshot().books.length});
})();