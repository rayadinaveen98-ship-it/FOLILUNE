/* FOLILUNE vNext G2-A — connected presentation surfaces.
 * All real reader actions route through a limited in-app bridge. This file
 * does not access IndexedDB, parse EPUBs, or modify the EPUB.js renderer.
 */
(function () {
  'use strict';
  const api=window.FOLILUNE_REAL_UI;
  if(!api || api.version!=='g2-bridge-1') return;
  const safe=s=>String(s==null?'':s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;','"':'&quot;',"'":'&#39;'}[c]));
  const ui={query:'',filter:'All',sort:'recent'};
  const coverClass=b=>['forest','coral','midnight','cream','sage','violet','sand'].includes(b.cover)?b.cover:'sage';
  const heading=(eyebrow,title,copy)=>'<div class="g2-heading"><div><div class="eyebrow">'+safe(eyebrow)+'</div><h1 class="page-title">'+safe(title)+'</h1><p class="page-sub">'+safe(copy)+'</p></div></div>';
  function tile(b,detail){
    return '<button class="g2-book" data-book="'+safe(b.id)+'" aria-label="Open '+safe(b.title)+'">'+
      '<span class="g2-book-cover '+coverClass(b)+'"><span class="g2-cover-moon">☾</span><span class="g2-cover-title">'+safe(b.title)+'</span><span class="g2-cover-author">'+safe(b.author)+'</span></span>'+
      '<span class="g2-book-title">'+safe(b.title)+'</span>'+
      '<span class="g2-book-sub">'+safe(b.author)+'</span>'+
      '<span class="g2-book-kind">'+safe(detail||(b.imported?'On this device':b.type+' · SAMPLE'))+'</span></button>';
  }
  const all=()=>api.snapshot().books;
  const local=()=>all().filter(b=>b.imported);
  function lastRead(list){return list.slice().sort((a,b)=>Number(b.lastOpened||0)-Number(a.lastOpened||0))[0];}
  function home(){
    const own=local(),recent=lastRead(own.filter(b=>b.lastOpened))||lastRead(own);
    const artwork='<div class="hero-art"></div>';
    const intro=recent?
      '<div class="eyebrow">YOUR STORY IS HERE</div><h2>'+safe(recent.title)+'</h2>'+
      '<p>Return to the page you last visited. Your reading position is stored on this device.</p>'+
      '<button class="primary" data-book="'+safe(recent.id)+'">Continue reading &nbsp; ↗</button>':
      '<div class="eyebrow">A WORLD BETWEEN PAGES</div><h2>Your next chapter starts here.</h2>'+
      '<p>Bring a book you own into a calm, private place to read. No account is required.</p>'+
      '<button class="primary" data-action="import-file">Add your first book &nbsp; ↗</button>';
    const samples=all().filter(b=>!b.imported).slice(0,3);
    return '<div class="content g2-content">'+heading('YOUR READING SANCTUARY','Welcome back to the page.','A beautiful space for the books you bring — ready whenever you are.')+
      '<section class="hero g2-hero"><div class="hero-copy">'+intro+'</div>'+artwork+
      '<div class="hero-progress">'+(own.length?safe(own.length)+' local '+(own.length===1?'book':'books'):'Your shelf is waiting')+'</div></section>'+
      '<section class="g2-section"><div class="section-title"><h2>On your reading desk</h2><button data-view="library">See your library ↗</button></div>'+
      (own.length?'<div class="g2-shelf">'+own.slice(0,5).map(b=>tile(b)).join('')+'</div>':
      '<div class="g2-empty"><span aria-hidden="true">✧</span><strong>Your library begins with a book.</strong><p>Import a DRM-free EPUB, TXT or Markdown file from this device. PDF and CBZ are not yet supported.</p><button class="secondary" data-action="import-file">Choose a book</button></div>')+
      '</section><section class="g2-section"><div class="section-title"><h2>Explore the reading experience</h2><span class="g2-honest">Interactive samples · not your books</span></div>'+
      '<div class="g2-shelf">'+samples.map(b=>tile(b)).join('')+'</div></section>'+
      '<div class="g2-assurance"><span>⌑</span><div><strong>Local-first reading</strong><p>Your imported files and saved locations stay in this app’s local storage. Uninstalling or clearing app data may erase them; cloud sync is not enabled.</p></div></div></div>';
  }
  function matches(b,q){return (b.title+' '+b.author+' '+b.type+' '+(b.format||'')).toLowerCase().includes(q);}
  function sorted(list){return list.slice().sort((a,b)=>ui.sort==='title'?a.title.localeCompare(b.title):Number(b.lastOpened||0)-Number(a.lastOpened||0)||a.title.localeCompare(b.title));}
  function library(){
    const own=local(),filtered=sorted(own.filter(b=>(ui.filter==='All'||b.type===ui.filter)&&matches(b,ui.query.toLowerCase())));
    return '<div class="content g2-content">'+heading('YOUR COLLECTION','A home for every world.','Your imported books, kept apart from our illustrative samples.')+
      '<div class="g2-toolbar"><label class="g2-search-input"><span aria-hidden="true">⌕</span><input id="g2LibraryQuery" type="search" value="'+safe(ui.query)+'" placeholder="Find a book or author" aria-label="Search imported books"></label>'+
      '<select id="g2Sort" aria-label="Sort library"><option value="recent"'+(ui.sort==='recent'?' selected':'')+'>Recently opened</option><option value="title"'+(ui.sort==='title'?' selected':'')+'>Title A–Z</option></select>'+
      '<button class="primary" data-action="import-file">＋ Add a book</button></div>'+
      '<div class="g2-filters" role="group" aria-label="Filter your library">'+['All','Novels','Comics','Study'].map(f=>'<button class="pill '+(ui.filter===f?'active':'')+'" data-g2-filter="'+safe(f)+'" aria-pressed="'+(ui.filter===f?'true':'false')+'">'+(f==='All'?'All':f==='Novels'?'Stories':f==='Comics'?'Comics':'Study')+'</button>').join('')+'</div>'+
      '<section class="g2-section"><div class="section-title"><h2>Your local books</h2><span class="g2-honest">'+filtered.length+' of '+own.length+'</span></div>'+
      '<div id="g2LibraryResults">'+(filtered.length?'<div class="g2-shelf">'+filtered.map(b=>tile(b)).join('')+'</div>':
      '<div class="g2-empty"><strong>'+((ui.query||ui.filter!=='All')?'No matches in your local library.':'Your library is ready for its first book.')+'</strong><p>'+(own.length?'Try a different search or filter.':'Use Add a book to import a file that belongs to you.')+'</p></div>')+'</div></section>'+
      '<section class="g2-section g2-sample-zone"><div class="section-title"><h2>Experience samples</h2><span class="g2-honest">Not imported · some are visual demonstrations only</span></div>'+
      '<div class="g2-shelf">'+all().filter(b=>!b.imported).slice(0,4).map(b=>tile(b)).join('')+'</div></section>'+
      '<div class="g2-assurance"><span>ⓘ</span><div><strong>About this reading test</strong><p>Original EPUB files are stored locally when imported successfully. Cloud sync is not enabled. Some sample titles are visual-only demonstrations, not full books.</p></div></div></div>';
  }
  function resultItems(q,scope){
    const pool=all().filter(b=>scope==='all'||(scope==='local'?b.imported:!b.imported));
    const filtered=sorted(pool.filter(b=>matches(b,q.toLowerCase().trim())));
    return filtered.length?'<div class="g2-shelf">'+filtered.map(b=>tile(b)).join('')+'</div>':
      '<div class="g2-empty"><strong>No matching titles.</strong><p>Search by book title, author or format. Only books available on this device and marked samples are searchable.</p></div>';
  }
  function search(){
    return '<div class="content g2-content">'+heading('DISCOVER YOUR SHELF','Find your next page.','Search your own books. FOLILUNE does not fetch titles from an online catalog.')+
      '<div class="g2-search-large"><span aria-hidden="true">⌕</span><input id="g2Search" type="search" value="'+safe(ui.query)+'" placeholder="Search books and authors" aria-label="Search your books" autocomplete="off"></div>'+
      '<div class="g2-section"><div class="section-title"><h2>Matching books</h2><span id="g2ResultCount" class="g2-honest"></span></div><div id="g2SearchResults">'+resultItems(ui.query,'all')+'</div></div></div>';
  }
  let savedEntries=[];
  function saved(){
    const s=api.snapshot();savedEntries=[];
    for(const b of s.books){
      const epubs=(s.epubBookmarks[b.id]||[]);
      epubs.forEach((cfi,i)=>{if(typeof cfi==='string'&&cfi.startsWith('epubcfi('))savedEntries.push({bookId:b.id,kind:'epub',cfi,label:'EPUB saved page '+(i+1),book:b})});
      const chapters=(s.textBookmarks[b.id]||[]);
      chapters.forEach((ch)=>{if(Number.isInteger(ch)&&ch>=0)savedEntries.push({bookId:b.id,kind:'chapter',chapter:ch,label:'Chapter '+(ch+1),book:b})});
    }
    return '<div class="content g2-content">'+heading('SAVED PLACES','The pages that stay with you.','Your real saved locations from local reading, without invented sample notes.')+
      (savedEntries.length?'<div class="g2-saved-list">'+savedEntries.map((x,i)=>
      '<button class="g2-saved-row" data-g2-open-saved="'+i+'"><span class="g2-save-mark">◇</span><span><strong>'+safe(x.book.title)+'</strong><small>'+safe(x.label)+' · '+(x.book.imported?'Local book':'Sample reader')+'</small></span><span aria-hidden="true">↗</span></button>').join('')+'</div>':
      '<div class="g2-empty"><strong>No saved pages yet.</strong><p>Open a book, use the bookmark control, and your places will appear here.</p><button class="secondary" data-view="library">Go to library</button></div>')+
      '<div class="g2-assurance"><span>⌑</span><div><strong>Saved on this device</strong><p>These are location references from your existing reading state. Book files and bookmarks are not synced to a Google account.</p></div></div></div>';
  }
  function settings(){
    const s=api.snapshot(); const font=Number(s.fontSize)||19;
    const tone=['paper','sepia','night'].includes(s.readerTone)?s.readerTone:'paper';
    const mode=document.body.classList.contains('vn-night')?'night':'paper';
    return '<div class="content g2-content">'+heading('MAKE IT YOURS','Reading comfort.','Quiet controls with real effects on your existing story reader.')+
      '<div class="g2-settings"><section class="g2-panel"><h2>App appearance</h2><p>Choose the atmosphere around your books. Your actual book page uses its own theme.</p>'+
      '<div class="g2-choice-row"><button data-g2-app-theme="paper" class="g2-choice '+(mode==='paper'?'selected':'')+'" aria-pressed="'+(mode==='paper')+'">☼ Warm paper</button><button data-g2-app-theme="night" class="g2-choice '+(mode==='night'?'selected':'')+'" aria-pressed="'+(mode==='night')+'">☾ Deep night</button></div></section>'+
      '<section class="g2-panel"><h2>Book page</h2><p>These settings apply to the real EPUB reader and supported text previews.</p>'+
      '<div class="g2-choice-row">'+['paper','sepia','night'].map(t=>'<button class="g2-choice '+(tone===t?'selected':'')+'" data-g2-tone="'+t+'" aria-pressed="'+(tone===t)+'">'+(t==='paper'?'Paper':t==='sepia'?'Sepia':'Night')+'</button>').join('')+'</div>'+
      '<div class="g2-step"><span>Text size</span><div><button data-g2-size="down" aria-label="Smaller reading text">−</button><output>'+font+' px</output><button data-g2-size="up" aria-label="Larger reading text">＋</button></div></div></section>'+
      '<section class="g2-panel"><h2>Library and privacy</h2><p>Your library is local to this test app. There is no account or cloud backup. Keep copies of original EPUBs before changing devices, clearing storage or uninstalling the APK.</p><button class="secondary" data-view="library">Open local library ↗</button></section>'+
      '<section class="g2-panel"><h2>About this build</h2><p>vNext G2-A experimental Android UI, built on real EPUB.js v12. Comics, PDF support, cloud publishing and synchronization are not enabled here.</p></section></div></div>';
  }
  api.register('home',home,'Your Reading Space');
  api.register('library',library,'Your Library');
  api.register('search',search,'Search');
  api.register('saved',saved,'Saved Places');
  api.register('settings',settings,'Settings');
  const nav=document.querySelector('.sidebar .nav-stack');
  if(nav&&!nav.querySelector('[data-view="search"]')){
    const slot=document.createElement('div');slot.className='g2-nav-extra';
    slot.innerHTML='<button class="nav-btn" data-view="search"><span class="nav-ico">⌕</span><span class="nav-label">Search</span></button>'+
      '<button class="nav-btn" data-view="saved"><span class="nav-ico">◇</span><span class="nav-label">Saved Places</span></button>'+
      '<button class="nav-btn" data-view="settings"><span class="nav-ico">⚙</span><span class="nav-label">Settings</span></button>';
    nav.querySelector('[data-view="library"]').after(slot);
    nav.querySelector('[data-view="comic"] .nav-label').textContent='Comic sample';
    nav.querySelector('[data-view="study"] .nav-label').textContent='Study sample';
  }
  const topInput=document.getElementById('globalSearch');
  if(topInput){topInput.setAttribute('placeholder','Search your books…');topInput.closest('label')?.setAttribute('title','Search imported books and samples');}
  document.addEventListener('click',e=>{
    const b=e.target.closest('[data-g2-filter],[data-g2-open-saved],[data-g2-tone],[data-g2-size],[data-g2-app-theme]');
    if(!b)return;
    e.preventDefault();
    if(b.dataset.g2Filter){ui.filter=b.dataset.g2Filter;api.navigate('library');return}
    if(b.dataset.g2OpenSaved!==undefined){const x=savedEntries[Number(b.dataset.g2OpenSaved)];if(x)api.openSaved(x);return}
    if(b.dataset.g2Tone){api.updateReading('readerTone',b.dataset.g2Tone);api.navigate('settings');return}
    if(b.dataset.g2Size){const current=api.snapshot().fontSize;api.updateReading('fontSize',Math.min(26,Math.max(16,current+(b.dataset.g2Size==='up'?1:-1))));api.navigate('settings');return}
    if(b.dataset.g2AppTheme){document.querySelector('#vnThemeToggle')?.click();api.navigate('settings');return}
  });
  document.addEventListener('input',e=>{
    if(e.target.id==='g2Search'){
      ui.query=e.target.value;
      const panel=document.getElementById('g2SearchResults');
      if(panel)panel.innerHTML=resultItems(ui.query,'all');
    }
    if(e.target.id==='g2LibraryQuery'){
      ui.query=e.target.value;
      const panel=document.getElementById('g2LibraryResults');
      if(panel){const matched=sorted(local().filter(b=>(ui.filter==='All'||b.type===ui.filter)&&matches(b,ui.query.toLowerCase())));
        panel.innerHTML=matched.length?'<div class="g2-shelf">'+matched.map(b=>tile(b)).join('')+'</div>':'<div class="g2-empty"><strong>No matching titles.</strong><p>Try another search.</p></div>';
      }
    }
  });
  document.addEventListener('change',e=>{if(e.target.id==='g2Sort'){ui.sort=e.target.value==='title'?'title':'recent';api.navigate('library')}});
  // Existing v12 import and EPUB state continue to drive all rerenders.
  // Do not call render here: loadImports() owns first Home hydration.
  window.FOLILUNE_G2=Object.freeze({version:'g2a',getSurface:()=>api.snapshot().view});
})();
