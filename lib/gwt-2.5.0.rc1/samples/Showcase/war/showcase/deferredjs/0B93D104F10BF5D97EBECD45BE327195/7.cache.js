function JVb(){KVb.call(this,false)}
function fWb(a,b){hWb.call(this,a,false);this.b=b}
function gWb(a,b){hWb.call(this,a,false);eWb(this,b)}
function iWb(a){hWb.call(this,'GWT',true);eWb(this,a)}
function Xjb(a){this.c=a;this.b=b4(this.c.a)}
function nVb(a,b){return uVb(a,b,a.a.b)}
function zd(a,b){Xb((ge(),be),a,dG(R$,llc,136,[(W9b(),b?V9b:U9b)]))}
function eWb(a,b){a.d=b;!!a.c&&IVb(a.c,a);if(b){b.cb.tabIndex=-1;se();zd(a.cb,true)}else{se();zd(a.cb,false)}}
function uVb(a,b,c){if(c<0||c>a.a.b){throw new N9b}a.o&&(b.cb[Asc]=2,undefined);mVb(a,c,b.cb);lgc(a.a,c,b);return b}
function Z3(a){var b,c;b=nG(a.a.kd(Ysc),150);if(b==null){c=dG(W$,mlc,1,['New','Open',Zsc,$sc,'Exit']);a.a.md(Ysc,c);return c}else{return b}}
function Y3(a){var b,c;b=nG(a.a.kd(Xsc),150);if(b==null){c=dG(W$,mlc,1,['Undo','Redo','Cut','Copy','Paste']);a.a.md(Xsc,c);return c}else{return b}}
function a4(a){var b,c;b=nG(a.a.kd(btc),150);if(b==null){c=dG(W$,mlc,1,['Contents','Fortune Cookie','About GWT']);a.a.md(btc,c);return c}else{return b}}
function _3(a){var b,c;b=nG(a.a.kd(atc),150);if(b==null){c=dG(W$,mlc,1,['Download','Examples',epc,"GWT wit' the program"]);a.a.md(atc,c);return c}else{return b}}
function $3(a){var b,c;b=nG(a.a.kd(_sc),150);if(b==null){c=dG(W$,mlc,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.a.md(_sc,c);return c}else{return b}}
function lWb(){var a;Eh(this,$doc.createElement(Fqc));this.cb[Smc]='gwt-MenuItemSeparator';a=$doc.createElement(Ymc);hIb(this.cb,a);a[Smc]='menuSeparatorInner'}
function b4(a){var b,c;b=nG(a.a.kd(ctc),150);if(b==null){c=dG(W$,mlc,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.a.md(ctc,c);return c}else{return b}}
function Tjb(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new Xjb(a);n=new JVb;n.b=true;n.cb.style[Tmc]='500px';n.e=true;q=new KVb(true);p=$3(a.a);for(k=0;k<p.length;++k){lVb(q,new fWb(p[k],o))}d=new KVb(true);d.e=true;lVb(n,new gWb('File',d));e=Z3(a.a);for(k=0;k<e.length;++k){if(k==3){nVb(d,new lWb);lVb(d,new gWb(e[3],q));nVb(d,new lWb)}else{lVb(d,new fWb(e[k],o))}}b=new KVb(true);lVb(n,new gWb('Edit',b));c=Y3(a.a);for(k=0;k<c.length;++k){lVb(b,new fWb(c[k],o))}f=new KVb(true);lVb(n,new iWb(f));g=_3(a.a);for(k=0;k<g.length;++k){lVb(f,new fWb(g[k],o))}i=new KVb(true);nVb(n,new lWb);lVb(n,new gWb('Help',i));j=a4(a.a);for(k=0;k<j.length;++k){lVb(i,new fWb(j[k],o))}P2b(n.cb,Qmc,dtc);HVb(n,dtc);return n}
var dtc='cwMenuBar',Xsc='cwMenuBarEditOptions',Ysc='cwMenuBarFileOptions',_sc='cwMenuBarFileRecents',atc='cwMenuBarGWTOptions',btc='cwMenuBarHelpOptions',ctc='cwMenuBarPrompts';Y_(660,1,{},Xjb);_.mc=function Yjb(){YIb(this.b[this.a]);this.a=(this.a+1)%this.b.length};_.a=0;_.c=null;Y_(661,1,_lc);_.kc=function akb(){G2(this.b,Tjb(this.a))};Y_(1057,102,olc,JVb);Y_(1064,103,{100:1,105:1,119:1},fWb,gWb,iWb);Y_(1065,103,{100:1,106:1,119:1},lWb);var gQ=pac(zrc,'CwMenuBar$1',660),UV=pac(xrc,'MenuItemSeparator',1065);Omc(km)(7);