/* FOLILUNE vNext Stage G1 — Safe additive UI bridge onto the real v12 EPUB engine.
 * Owns only appearance and first-use microcopy. Does not touch EPUB/IDB/CFI code.
 */
(function(){
  'use strict';
  const VN_KEY='folilune_vnext_integrated_g1_visual';
  const app=document.getElementById('app');
  if(!app)return;
  const read=()=>{try{return JSON.parse(localStorage.getItem(VN_KEY)||'null')||{}}catch(e){return {}}};
  const initial=read();
  const prefs={theme:initial.theme==='night'?'night':'paper',
    seen:Boolean(initial.seen),readerSize:Number.isFinite(initial.readerSize)?initial.readerSize:19};
  const store=()=>{try{localStorage.setItem(VN_KEY,JSON.stringify(prefs))}catch(e){}};
  const logo=(color='currentColor')=>
    '<svg role="img" aria-label="Folded Moon and open book, provisional FOLILUNE mark" viewBox="0 0 120 120" fill="none" xmlns="http://www.w3.org/2000/svg" style="color:'+color+'">'+
    '<path d="M89 24C67 13 42 31 36 57" stroke="currentColor" stroke-width="6.2" stroke-linecap="round"/>'+
    '<path d="M24 76Q43 68 60 84Q77 68 96 76V98Q76 91 60 106Q44 91 24 98Z" stroke="currentColor" stroke-width="5.6" stroke-linejoin="round"/>'+
    '<path d="M60 84V105" stroke="currentColor" stroke-width="3.8" stroke-linecap="round"/></svg>';
  const glyph=(type)=>{
    const paths={
      home:'<path d="m3 10 9-7 9 7v10a1 1 0 0 1-1 1h-5v-7H9v7H4a1 1 0 0 1-1-1V10Z"/>',
      library:'<path d="M3 4h5v17H3zM10 4h5v17h-5zM17 6h4v15h-4z"/>',
      story:'<path d="M12 7v14M3 4h7a2 2 0 0 1 2 2v15a4 4 0 0 0-4-3H3zM21 4h-7a2 2 0 0 0-2 2v15a4 4 0 0 1 4-3h5z"/>',
      comic:'<rect x="3" y="3" width="18" height="18" rx="2"/><path d="M3 12h18M12 3v18"/>',
      study:'<path d="M4 19h16M6 16 16 6l3 3-10 10H6zM15 7l2-2 3 3-2 2"/>',
      moon:'<path d="M20.5 13A8.5 8.5 0 0 1 11 3.5 8.5 8.5 0 1 0 20.5 13Z"/>',
      replay:'<path d="M3 11a9 9 0 1 1 3 7M3 4v7h7"/>'
    };
    return '<svg aria-hidden="true" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">'+(paths[type]||paths.story)+'</svg>';
  };
  const bookArt=()=>`<svg viewBox="0 0 620 330" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Original FOLILUNE artwork: an open book beneath a crescent moon">
    <defs>
      <linearGradient id="vnSky" x1="0" x2="0" y1="0" y2="1">
        <stop offset="0" stop-color="#173a39"/><stop offset=".6" stop-color="#527767"/><stop offset="1" stop-color="#93a99b"/>
      </linearGradient>
      <linearGradient id="vnLeft" x1="0" x2="1"><stop stop-color="#d1b98a"/><stop offset="1" stop-color="#fff7e7"/></linearGradient>
      <linearGradient id="vnRight" x1="1" x2="0"><stop stop-color="#eee2cd"/><stop offset="1" stop-color="#fffaf0"/></linearGradient>
      <radialGradient id="vnGlow"><stop stop-color="#F7E4B9" stop-opacity=".68"/><stop offset="1" stop-color="#F7E4B9" stop-opacity="0"/></radialGradient>
    </defs>
    <rect width="620" height="330" fill="url(#vnSky)"/><circle cx="370" cy="86" r="135" fill="url(#vnGlow)"/>
    <circle cx="392" cy="91" r="52" fill="#fcebc9"/><circle cx="412" cy="75" r="50" fill="#416b60"/>
    <g fill="#fff2cc"><circle cx="130" cy="56" r="2.5"/><circle cx="229" cy="94" r="2"/><circle cx="519" cy="49" r="2.5"/><circle cx="463" cy="148" r="2"/><circle cx="73" cy="156" r="1.5"/></g>
    <path d="M0 248Q114 189 208 241Q300 175 409 226Q514 186 620 236V330H0Z" fill="#456d5f" opacity=".72"/>
    <path d="M0 280Q128 244 220 278Q372 232 480 260Q565 245 620 274V330H0Z" fill="#284b46"/>
    <ellipse cx="321" cy="291" rx="228" ry="24" fill="#1a3632" opacity=".5"/>
    <g transform="translate(88 198)">
      <path d="M31 46Q131 24 234 63Q340 22 457 50L439 99Q325 78 234 103Q130 83 45 96Z" fill="#d5bea1" opacity=".6"/>
      <path d="M42 34Q141 5 234 51Q334 7 447 31L439 93Q319 70 234 100Q128 70 48 90Z" fill="url(#vnLeft)" stroke="#e5d0ad" stroke-width="3"/>
      <path d="M234 51Q334 7 447 31L439 93Q319 70 234 100Z" fill="url(#vnRight)" stroke="#d7c2a1" stroke-width="3"/>
      <path d="M234 50v51" stroke="#826954" stroke-width="2.5"/><path d="M62 55Q151 32 211 65M64 66Q160 44 214 78M258 68Q340 40 427 50M260 80Q349 54 426 63" fill="none" stroke="#9d886f" stroke-opacity=".55" stroke-width="2"/>
    </g>
    <g fill="#f3dca9"><circle cx="212" cy="194" r="3"/><circle cx="463" cy="217" r="2"/><circle cx="351" cy="164" r="2.5"/></g>
  </svg>`;
  const applyTheme=()=>{
    document.body.classList.toggle('vn-night',prefs.theme==='night');
    const t=document.getElementById('vnThemeToggle');
    if(t)t.setAttribute('aria-label',prefs.theme==='night'?'Switch to paper theme':'Switch to night theme');
  };
  // The three EPUB.js engine functions and all book storage keys remain unchanged.
  // Reassign only an existing decorative art function, never a reader function.
  try{if(typeof heroArt==='function')heroArt=bookArt}catch(e){}
  const mark=document.querySelector('.brand-mark');
  if(mark){mark.textContent='';mark.insertAdjacentHTML('beforeend',logo());}
  document.querySelectorAll('.nav-btn[data-view] .nav-ico').forEach(el=>{
    const type=el.parentElement.dataset.view;
    el.innerHTML=glyph(type==='comic'?'comic':type);
    el.querySelector('svg')?.setAttribute('width','22');
    el.querySelector('svg')?.setAttribute('height','22');
  });
  const bar=document.querySelector('.topbar-right');
  if(bar){
    const group=document.createElement('div');group.className='vn-controls';
    group.innerHTML='<button id="vnThemeToggle" type="button" class="vn-control" title="Paper / night theme">'+glyph('moon')+
      '</button><button id="vnReplay" type="button" class="vn-control vn-control-icon" title="Replay welcome">'+glyph('replay')+
      '<span class="vn-title">Welcome</span></button>';
    bar.prepend(group);
    group.querySelector('#vnThemeToggle').addEventListener('click',()=>{
      prefs.theme=prefs.theme==='night'?'paper':'night';store();applyTheme();
    });
    group.querySelector('#vnReplay').addEventListener('click',()=>openWelcome());
  }
  const flag=document.querySelector('.demo-flag');
  if(flag)flag.textContent='✦ FOLILUNE vNext · Real EPUB Reader / G1 Test';
  const welcome=document.createElement('section');
  welcome.id='vnWelcome';
  welcome.hidden=true;
  welcome.setAttribute('role','dialog');
  welcome.setAttribute('aria-modal','true');
  welcome.setAttribute('aria-label','Welcome to FOLILUNE');
  welcome.innerHTML='<div class="vn-entry">'+
    '<div class="vn-entry-copy">'+
      '<div class="vn-entry-name">'+logo()+'<span>FOLILUNE</span></div>'+
      '<div class="vn-entry-kicker" id="vnKicker">A world between pages</div>'+
      '<h1 id="vnHeadline">A quieter world for every story.</h1>'+
      '<p id="vnDescription">A personal reading sanctuary for the stories you bring, the pages you revisit, and the worlds you discover.</p>'+
      '<div id="vnComfort" class="vn-hidden">'+
        '<label>Choose your reading atmosphere</label>'+
        '<div class="vn-set-row" role="group" aria-label="Appearance">'+
          '<button class="vn-quiet vn-choice" data-vn-theme="paper" type="button">Warm paper</button>'+
          '<button class="vn-quiet vn-choice" data-vn-theme="night" type="button">Deep night</button>'+
        '</div><label for="vnFontSize">Reading text comfort</label>'+
        '<div class="vn-size-row"><span aria-hidden="true">A−</span>'+
          '<input id="vnFontSize" type="range" min="16" max="26" value="19" step="1" aria-label="Preferred reading text size"/>'+
          '<output id="vnFontOutput" for="vnFontSize">19 px</output></div>'+
      '</div>'+
      '<div class="vn-entry-actions">'+
        '<button id="vnContinue" class="vn-start" type="button">Personalize my reading →</button>'+
        '<button id="vnSkip" class="vn-quiet" type="button">Skip and open library</button>'+
      '</div>'+
      '<div class="vn-entry-footnote">Local-first reading · No account required · Original sample artwork</div>'+
    '</div><div class="vn-entry-art">'+bookArt()+'</div></div>';
  document.body.appendChild(welcome);
  let step=0,lastFocus=null;
  const setScreen=()=>{
    const comfort=step===1;
    welcome.querySelector('#vnKicker').textContent=comfort?'Make the page yours':'A world between pages';
    welcome.querySelector('#vnHeadline').textContent=comfort?'Your pace. Your pages.':'A quieter world for every story.';
    welcome.querySelector('#vnDescription').textContent=comfort?
      'Choose how FOLILUNE looks when you arrive. Your book pages have independent reading controls, too.':
      'A personal reading sanctuary for the stories you bring, the pages you revisit, and the worlds you discover.';
    welcome.querySelector('#vnComfort').classList.toggle('vn-hidden',!comfort);
    welcome.querySelector('#vnContinue').textContent=comfort?'Enter my reading space →':'Personalize my reading →';
    welcome.querySelector('#vnSkip').textContent=comfort?'Not now':'Skip and open library';
    welcome.querySelectorAll('[data-vn-theme]').forEach(b=>b.setAttribute('aria-pressed',String(b.dataset.vnTheme===prefs.theme)));
    const range=welcome.querySelector('#vnFontSize');range.value=String(Math.max(16,Math.min(26,prefs.readerSize)));
    welcome.querySelector('#vnFontOutput').textContent=range.value+' px';
  };
  function closeWelcome(applyComfort){
    if(applyComfort){
      // This is the EXISTING reader preference; do not reset other state fields.
      if(typeof state!=='undefined'&&state && typeof save==='function'){
        state.fontSize=Math.max(16,Math.min(26,prefs.readerSize));
        save();
      }
    }
    welcome.hidden=true;
    welcome.setAttribute('aria-hidden','true');
    app.inert=false;document.body.style.overflow='';
    prefs.seen=true;store();applyTheme();
    if(lastFocus?.isConnected)lastFocus.focus({preventScroll:true});
    else document.querySelector('.nav-btn[data-view=library]')?.focus({preventScroll:true});
  }
  function openWelcome(){
    lastFocus=document.activeElement;
    step=0;setScreen();
    welcome.hidden=false;welcome.removeAttribute('aria-hidden');
    app.inert=true;document.body.style.overflow='hidden';
    welcome.querySelector('#vnContinue').focus({preventScroll:true});
  }
  welcome.addEventListener('click',e=>{
    const b=e.target.closest('button');
    if(!b)return;
    if(b.dataset.vnTheme){
      prefs.theme=b.dataset.vnTheme;store();applyTheme();setScreen();return;
    }
    if(b.id==='vnSkip'){closeWelcome(false);return}
    if(b.id==='vnContinue'){if(step===0){step=1;setScreen();b.focus()}else closeWelcome(true);}
  });
  welcome.querySelector('#vnFontSize').addEventListener('input',e=>{
    prefs.readerSize=Number(e.target.value);welcome.querySelector('#vnFontOutput').textContent=prefs.readerSize+' px';
  });
  welcome.addEventListener('keydown',e=>{
    if(e.key==='Escape'){e.preventDefault();closeWelcome(false);}
    if(e.key==='Tab'){
      const controls=[...welcome.querySelectorAll('button:not([disabled]),input:not([disabled])')]
        .filter(el=>el.offsetParent!==null);
      if(!controls.length)return;
      const first=controls[0],last=controls[controls.length-1];
      if(e.shiftKey&&document.activeElement===first){e.preventDefault();last.focus()}
      else if(!e.shiftKey&&document.activeElement===last){e.preventDefault();first.focus()}
    }
  });
  applyTheme();
  // Re-render once to use the new decorative hero art; the real EPUB engine is untouched.
  if(typeof render==='function'&&typeof state!=='undefined'&&state.view==='home')render();
  if(!prefs.seen)openWelcome();
  window.FOLILUNE_G1={version:'g1',engine:'v12-unmodified',showWelcome:openWelcome,
    isWelcomeOpen:()=>!welcome.hidden};
})();
