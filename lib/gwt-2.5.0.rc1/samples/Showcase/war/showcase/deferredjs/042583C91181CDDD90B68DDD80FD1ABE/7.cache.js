function pWb(){qWb.call(this,false)}
function NWb(a,b){PWb.call(this,a,false);this.c=b}
function OWb(a,b){PWb.call(this,a,false);MWb(this,b)}
function QWb(a){PWb.call(this,'GWT',true);MWb(this,a)}
function Gkb(a){this.d=a;this.c=M4(this.d.b)}
function UVb(a,b){return aWb(a,b,a.b.c)}
function Ld(a,b){hc((se(),ne),a,LG(F_,Zlc,135,[(Gac(),b?Fac:Eac)]))}
function aWb(a,b,c){if(c<0||c>a.b.c){throw new xac}a.p&&(b.db[wtc]=2,undefined);TVb(a,c,b.db);Ygc(a.b,c,b);return b}
function MWb(a,b){a.e=b;!!a.d&&oWb(a.d,a);if(b){(rTb(),b.db).tabIndex=-1;Ee();Ld(a.db,true)}else{Ee();Ld(a.db,false)}}
function I4(a){var b,c;b=VG(a.b.sd(Utc),149);if(b==null){c=LG(K_,$lc,1,['New','Open',Vtc,Wtc,'Exit']);a.b.ud(Utc,c);return c}else{return b}}
function H4(a){var b,c;b=VG(a.b.sd(Ttc),149);if(b==null){c=LG(K_,$lc,1,['Undo','Redo','Cut','Copy','Paste']);a.b.ud(Ttc,c);return c}else{return b}}
function L4(a){var b,c;b=VG(a.b.sd(Ztc),149);if(b==null){c=LG(K_,$lc,1,['Contents','Fortune Cookie','About GWT']);a.b.ud(Ztc,c);return c}else{return b}}
function K4(a){var b,c;b=VG(a.b.sd(Ytc),149);if(b==null){c=LG(K_,$lc,1,['Download','Examples',aqc,"GWT wit' the program"]);a.b.ud(Ytc,c);return c}else{return b}}
function J4(a){var b,c;b=VG(a.b.sd(Xtc),149);if(b==null){c=LG(K_,$lc,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.b.ud(Xtc,c);return c}else{return b}}
function TWb(){var a;Qh(this,$doc.createElement(Crc));this.db[Fnc]='gwt-MenuItemSeparator';a=$doc.createElement(Lnc);NIb(this.db,a);a[Fnc]='menuSeparatorInner'}
function M4(a){var b,c;b=VG(a.b.sd($tc),149);if(b==null){c=LG(K_,$lc,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.b.ud($tc,c);return c}else{return b}}
function Ckb(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new Gkb(a);n=new pWb;n.c=true;n.db.style[Gnc]='500px';n.f=true;q=new qWb(true);p=J4(a.b);for(k=0;k<p.length;++k){SVb(q,new NWb(p[k],o))}d=new qWb(true);d.f=true;SVb(n,new OWb('File',d));e=I4(a.b);for(k=0;k<e.length;++k){if(k==3){UVb(d,new TWb);SVb(d,new OWb(e[3],q));UVb(d,new TWb)}else{SVb(d,new NWb(e[k],o))}}b=new qWb(true);SVb(n,new OWb('Edit',b));c=H4(a.b);for(k=0;k<c.length;++k){SVb(b,new NWb(c[k],o))}f=new qWb(true);SVb(n,new QWb(f));g=K4(a.b);for(k=0;k<g.length;++k){SVb(f,new NWb(g[k],o))}i=new qWb(true);UVb(n,new TWb);SVb(n,new OWb('Help',i));j=L4(a.b);for(k=0;k<j.length;++k){SVb(i,new NWb(j[k],o))}r3b(n.db,Dnc,_tc);nWb(n,_tc);return n}
var _tc='cwMenuBar',Ttc='cwMenuBarEditOptions',Utc='cwMenuBarFileOptions',Xtc='cwMenuBarFileRecents',Ytc='cwMenuBarGWTOptions',Ztc='cwMenuBarHelpOptions',$tc='cwMenuBarPrompts';M0(662,1,{},Gkb);_.rc=function Hkb(){DJb(this.c[this.b]);this.b=(this.b+1)%this.c.length};_.b=0;_.d=null;M0(663,1,Nmc);_.pc=function Lkb(){p3(this.c,Ckb(this.b))};M0(1056,104,amc,pWb);M0(1063,105,{99:1,104:1,118:1},NWb,OWb,QWb);M0(1064,105,{99:1,105:1,118:1},TWb);var VQ=_ac(wsc,'CwMenuBar$1',662),GW=_ac(usc,'MenuItemSeparator',1064);Anc(xm)(7);