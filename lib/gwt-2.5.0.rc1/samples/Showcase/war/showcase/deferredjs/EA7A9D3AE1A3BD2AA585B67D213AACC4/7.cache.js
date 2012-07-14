function _Vb(){aWb.call(this,false)}
function xWb(a,b){zWb.call(this,a,false);this.b=b}
function yWb(a,b){zWb.call(this,a,false);wWb(this,b)}
function AWb(a){zWb.call(this,'GWT',true);wWb(this,a)}
function gkb(a){this.c=a;this.b=m4(this.c.a)}
function FVb(a,b){return MVb(a,b,a.a.b)}
function zd(a,b){Xb((ge(),be),a,oG(a_,Flc,136,[(oac(),b?nac:mac)]))}
function wWb(a,b){a.d=b;!!a.c&&$Vb(a.c,a);if(b){b.cb.tabIndex=-1;se();zd(a.cb,true)}else{se();zd(a.cb,false)}}
function MVb(a,b,c){if(c<0||c>a.a.b){throw new fac}a.o&&(b.cb[Wsc]=2,undefined);EVb(a,c,b.cb);Fgc(a.a,c,b);return b}
function DWb(){var a;Eh(this,nq($doc,$qc));this.cb[knc]='gwt-MenuItemSeparator';a=nq($doc,rnc);GIb(this.cb,a);a[knc]='menuSeparatorInner'}
function i4(a){var b,c;b=yG(a.a.kd(stc),150);if(b==null){c=oG(f_,Glc,1,['New','Open',ttc,utc,'Exit']);a.a.md(stc,c);return c}else{return b}}
function h4(a){var b,c;b=yG(a.a.kd(rtc),150);if(b==null){c=oG(f_,Glc,1,['Undo','Redo','Cut','Copy','Paste']);a.a.md(rtc,c);return c}else{return b}}
function l4(a){var b,c;b=yG(a.a.kd(xtc),150);if(b==null){c=oG(f_,Glc,1,['Contents','Fortune Cookie','About GWT']);a.a.md(xtc,c);return c}else{return b}}
function k4(a){var b,c;b=yG(a.a.kd(wtc),150);if(b==null){c=oG(f_,Glc,1,['Download','Examples',Apc,"GWT wit' the program"]);a.a.md(wtc,c);return c}else{return b}}
function j4(a){var b,c;b=yG(a.a.kd(vtc),150);if(b==null){c=oG(f_,Glc,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.a.md(vtc,c);return c}else{return b}}
function m4(a){var b,c;b=yG(a.a.kd(ytc),150);if(b==null){c=oG(f_,Glc,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.a.md(ytc,c);return c}else{return b}}
function ckb(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new gkb(a);n=new _Vb;n.b=true;n.cb.style[lnc]='500px';n.e=true;q=new aWb(true);p=j4(a.a);for(k=0;k<p.length;++k){DVb(q,new xWb(p[k],o))}d=new aWb(true);d.e=true;DVb(n,new yWb('File',d));e=i4(a.a);for(k=0;k<e.length;++k){if(k==3){FVb(d,new DWb);DVb(d,new yWb(e[3],q));FVb(d,new DWb)}else{DVb(d,new xWb(e[k],o))}}b=new aWb(true);DVb(n,new yWb('Edit',b));c=h4(a.a);for(k=0;k<c.length;++k){DVb(b,new xWb(c[k],o))}f=new aWb(true);DVb(n,new AWb(f));g=k4(a.a);for(k=0;k<g.length;++k){DVb(f,new xWb(g[k],o))}i=new aWb(true);FVb(n,new DWb);DVb(n,new yWb('Help',i));j=l4(a.a);for(k=0;k<j.length;++k){DVb(i,new xWb(j[k],o))}f3b(n.cb,inc,ztc);ZVb(n,ztc);return n}
var ztc='cwMenuBar',rtc='cwMenuBarEditOptions',stc='cwMenuBarFileOptions',vtc='cwMenuBarFileRecents',wtc='cwMenuBarGWTOptions',xtc='cwMenuBarHelpOptions',ytc='cwMenuBarPrompts';h0(659,1,{},gkb);_.mc=function hkb(){vJb(this.b[this.a]);this.a=(this.a+1)%this.b.length};_.a=0;_.c=null;h0(660,1,tmc);_.kc=function lkb(){R2(this.b,ckb(this.a))};h0(1054,102,Ilc,_Vb);h0(1061,103,{100:1,105:1,119:1},xWb,yWb,AWb);h0(1062,103,{100:1,106:1,119:1},DWb);var rQ=Jac(Vrc,'CwMenuBar$1',659),dW=Jac(Trc,'MenuItemSeparator',1062);gnc(jm)(7);