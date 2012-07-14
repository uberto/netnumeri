function XVb(){YVb.call(this,false)}
function tWb(a,b){vWb.call(this,a,false);this.c=b}
function uWb(a,b){vWb.call(this,a,false);sWb(this,b)}
function wWb(a){vWb.call(this,'GWT',true);sWb(this,a)}
function nkb(a){this.d=a;this.c=t4(this.d.b)}
function BVb(a,b){return IVb(a,b,a.b.c)}
function Kd(a,b){gc((re(),me),a,yG(m_,Alc,135,[(iac(),b?hac:gac)]))}
function sWb(a,b){a.e=b;!!a.d&&WVb(a.d,a);if(b){b.db.tabIndex=-1;De();Kd(a.db,true)}else{De();Kd(a.db,false)}}
function IVb(a,b,c){if(c<0||c>a.b.c){throw new _9b}a.p&&(b.db[Zsc]=2,undefined);AVb(a,c,b.db);zgc(a.b,c,b);return b}
function p4(a){var b,c;b=IG(a.b.od(vtc),149);if(b==null){c=yG(r_,Blc,1,['New','Open',wtc,xtc,'Exit']);a.b.qd(vtc,c);return c}else{return b}}
function o4(a){var b,c;b=IG(a.b.od(utc),149);if(b==null){c=yG(r_,Blc,1,['Undo','Redo','Cut','Copy','Paste']);a.b.qd(utc,c);return c}else{return b}}
function s4(a){var b,c;b=IG(a.b.od(Atc),149);if(b==null){c=yG(r_,Blc,1,['Contents','Fortune Cookie','About GWT']);a.b.qd(Atc,c);return c}else{return b}}
function r4(a){var b,c;b=IG(a.b.od(ztc),149);if(b==null){c=yG(r_,Blc,1,['Download','Examples',vpc,"GWT wit' the program"]);a.b.qd(ztc,c);return c}else{return b}}
function q4(a){var b,c;b=IG(a.b.od(ytc),149);if(b==null){c=yG(r_,Blc,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.b.qd(ytc,c);return c}else{return b}}
function zWb(){var a;Ph(this,$doc.createElement(crc));this.db[fnc]='gwt-MenuItemSeparator';a=$doc.createElement(lnc);xIb(this.db,a);a[fnc]='menuSeparatorInner'}
function t4(a){var b,c;b=IG(a.b.od(Btc),149);if(b==null){c=yG(r_,Blc,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.b.qd(Btc,c);return c}else{return b}}
function jkb(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new nkb(a);n=new XVb;n.c=true;n.db.style[gnc]='500px';n.f=true;q=new YVb(true);p=q4(a.b);for(k=0;k<p.length;++k){zVb(q,new tWb(p[k],o))}d=new YVb(true);d.f=true;zVb(n,new uWb('File',d));e=p4(a.b);for(k=0;k<e.length;++k){if(k==3){BVb(d,new zWb);zVb(d,new uWb(e[3],q));BVb(d,new zWb)}else{zVb(d,new tWb(e[k],o))}}b=new YVb(true);zVb(n,new uWb('Edit',b));c=o4(a.b);for(k=0;k<c.length;++k){zVb(b,new tWb(c[k],o))}f=new YVb(true);zVb(n,new wWb(f));g=r4(a.b);for(k=0;k<g.length;++k){zVb(f,new tWb(g[k],o))}i=new YVb(true);BVb(n,new zWb);zVb(n,new uWb('Help',i));j=s4(a.b);for(k=0;k<j.length;++k){zVb(i,new tWb(j[k],o))}Z2b(n.db,dnc,Ctc);VVb(n,Ctc);return n}
var Ctc='cwMenuBar',utc='cwMenuBarEditOptions',vtc='cwMenuBarFileOptions',ytc='cwMenuBarFileRecents',ztc='cwMenuBarGWTOptions',Atc='cwMenuBarHelpOptions',Btc='cwMenuBarPrompts';t0(658,1,{},nkb);_.rc=function okb(){mJb(this.c[this.b]);this.b=(this.b+1)%this.c.length};_.b=0;_.d=null;t0(659,1,omc);_.pc=function skb(){Y2(this.c,jkb(this.b))};t0(1053,104,Dlc,XVb);t0(1060,105,{99:1,104:1,118:1},tWb,uWb,wWb);t0(1061,105,{99:1,105:1,118:1},zWb);var EQ=Dac(Yrc,'CwMenuBar$1',658),pW=Dac(Wrc,'MenuItemSeparator',1061);bnc(wm)(7);