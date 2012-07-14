function qVb(){rVb.call(this,false)}
function OVb(a,b){QVb.call(this,a,false);this.c=b}
function PVb(a,b){QVb.call(this,a,false);NVb(this,b)}
function RVb(a){QVb.call(this,'GWT',true);NVb(this,a)}
function Ljb(a){this.d=a;this.c=R3(this.d.b)}
function WUb(a,b){return bVb(a,b,a.b.c)}
function zd(a,b){Xb((ge(),be),a,ZF(K$,Jkc,135,[(s9b(),b?r9b:q9b)]))}
function NVb(a,b){a.e=b;!!a.d&&pVb(a.d,a);if(b){b.db.tabIndex=-1;se();zd(a.db,true)}else{se();zd(a.db,false)}}
function bVb(a,b,c){if(c<0||c>a.b.c){throw new j9b}a.p&&(b.db[asc]=2,undefined);VUb(a,c,b.db);Jfc(a.b,c,b);return b}
function N3(a){var b,c;b=hG(a.b.kd(ysc),149);if(b==null){c=ZF(P$,Kkc,1,['New','Open',zsc,Asc,'Exit']);a.b.md(ysc,c);return c}else{return b}}
function M3(a){var b,c;b=hG(a.b.kd(xsc),149);if(b==null){c=ZF(P$,Kkc,1,['Undo','Redo','Cut','Copy','Paste']);a.b.md(xsc,c);return c}else{return b}}
function Q3(a){var b,c;b=hG(a.b.kd(Dsc),149);if(b==null){c=ZF(P$,Kkc,1,['Contents','Fortune Cookie','About GWT']);a.b.md(Dsc,c);return c}else{return b}}
function P3(a){var b,c;b=hG(a.b.kd(Csc),149);if(b==null){c=ZF(P$,Kkc,1,['Download','Examples',Foc,"GWT wit' the program"]);a.b.md(Csc,c);return c}else{return b}}
function O3(a){var b,c;b=hG(a.b.kd(Bsc),149);if(b==null){c=ZF(P$,Kkc,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.b.md(Bsc,c);return c}else{return b}}
function UVb(){var a;Eh(this,$doc.createElement(gqc));this.db[omc]='gwt-MenuItemSeparator';a=$doc.createElement(umc);SHb(this.db,a);a[omc]='menuSeparatorInner'}
function R3(a){var b,c;b=hG(a.b.kd(Esc),149);if(b==null){c=ZF(P$,Kkc,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.b.md(Esc,c);return c}else{return b}}
function Hjb(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new Ljb(a);n=new qVb;n.c=true;n.db.style[pmc]='500px';n.f=true;q=new rVb(true);p=O3(a.b);for(k=0;k<p.length;++k){UUb(q,new OVb(p[k],o))}d=new rVb(true);d.f=true;UUb(n,new PVb('File',d));e=N3(a.b);for(k=0;k<e.length;++k){if(k==3){WUb(d,new UVb);UUb(d,new PVb(e[3],q));WUb(d,new UVb)}else{UUb(d,new OVb(e[k],o))}}b=new rVb(true);UUb(n,new PVb('Edit',b));c=M3(a.b);for(k=0;k<c.length;++k){UUb(b,new OVb(c[k],o))}f=new rVb(true);UUb(n,new RVb(f));g=P3(a.b);for(k=0;k<g.length;++k){UUb(f,new OVb(g[k],o))}i=new rVb(true);WUb(n,new UVb);UUb(n,new PVb('Help',i));j=Q3(a.b);for(k=0;k<j.length;++k){UUb(i,new OVb(j[k],o))}s2b(n.db,mmc,Fsc);oVb(n,Fsc);return n}
var Fsc='cwMenuBar',xsc='cwMenuBarEditOptions',ysc='cwMenuBarFileOptions',Bsc='cwMenuBarFileRecents',Csc='cwMenuBarGWTOptions',Dsc='cwMenuBarHelpOptions',Esc='cwMenuBarPrompts';R_(657,1,{},Ljb);_.nc=function Mjb(){IIb(this.c[this.b]);this.b=(this.b+1)%this.c.length};_.b=0;_.d=null;R_(658,1,xlc);_.lc=function Qjb(){u2(this.c,Hjb(this.b))};R_(1051,102,Mkc,qVb);R_(1058,103,{99:1,104:1,118:1},OVb,PVb,RVb);R_(1059,103,{99:1,105:1,118:1},UVb);var bQ=N9b(_qc,'CwMenuBar$1',657),OV=N9b(Zqc,'MenuItemSeparator',1059);kmc(km)(7);