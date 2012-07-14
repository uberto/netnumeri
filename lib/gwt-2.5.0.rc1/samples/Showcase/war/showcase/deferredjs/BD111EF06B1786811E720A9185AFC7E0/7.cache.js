function BBc(){CBc.call(this,false)}
function ZBc(a,b){_Bc.call(this,a,false);this.c=b}
function $Bc(a,b){_Bc.call(this,a,false);YBc(this,b)}
function aCc(a){_Bc.call(this,'GWT',true);YBc(this,a)}
function S_b(a){this.d=a;this.c=YLb(this.d.b)}
function eBc(a,b){return mBc(a,b,a.b.c)}
function Ld(a,b){hc((se(),ne),a,qkb(RGb,j1c,136,[(SRc(),b?RRc:QRc)]))}
function mBc(a,b,c){if(c<0||c>a.b.c){throw new JRc}a.p&&(b.db[g9c]=2,undefined);dBc(a,c,b.db);iYc(a.b,c,b);return b}
function YBc(a,b){a.e=b;!!a.d&&ABc(a.d,a);if(b){(Dyc(),b.db).tabIndex=-1;Ee();Ld(a.db,true)}else{Ee();Ld(a.db,false)}}
function ULb(a){var b,c;b=Akb(a.b.pe(E9c),150);if(b==null){c=qkb(WGb,k1c,1,['New','Open',F9c,G9c,'Exit']);a.b.re(E9c,c);return c}else{return b}}
function TLb(a){var b,c;b=Akb(a.b.pe(D9c),150);if(b==null){c=qkb(WGb,k1c,1,['Undo','Redo','Cut','Copy','Paste']);a.b.re(D9c,c);return c}else{return b}}
function XLb(a){var b,c;b=Akb(a.b.pe(J9c),150);if(b==null){c=qkb(WGb,k1c,1,['Contents','Fortune Cookie','About GWT']);a.b.re(J9c,c);return c}else{return b}}
function WLb(a){var b,c;b=Akb(a.b.pe(I9c),150);if(b==null){c=qkb(WGb,k1c,1,['Download','Examples',M5c,"GWT wit' the program"]);a.b.re(I9c,c);return c}else{return b}}
function VLb(a){var b,c;b=Akb(a.b.pe(H9c),150);if(b==null){c=qkb(WGb,k1c,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.b.re(H9c,c);return c}else{return b}}
function dCc(){var a;Qh(this,$doc.createElement(m7c));this.db[R2c]='gwt-MenuItemSeparator';a=$doc.createElement(X2c);Znc(this.db,a);a[R2c]='menuSeparatorInner'}
function YLb(a){var b,c;b=Akb(a.b.pe(K9c),150);if(b==null){c=qkb(WGb,k1c,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.b.re(K9c,c);return c}else{return b}}
function O_b(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new S_b(a);n=new BBc;n.c=true;n.db.style[S2c]='500px';n.f=true;q=new CBc(true);p=VLb(a.b);for(k=0;k<p.length;++k){cBc(q,new ZBc(p[k],o))}d=new CBc(true);d.f=true;cBc(n,new $Bc('File',d));e=ULb(a.b);for(k=0;k<e.length;++k){if(k==3){eBc(d,new dCc);cBc(d,new $Bc(e[3],q));eBc(d,new dCc)}else{cBc(d,new ZBc(e[k],o))}}b=new CBc(true);cBc(n,new $Bc('Edit',b));c=TLb(a.b);for(k=0;k<c.length;++k){cBc(b,new ZBc(c[k],o))}f=new CBc(true);cBc(n,new aCc(f));g=WLb(a.b);for(k=0;k<g.length;++k){cBc(f,new ZBc(g[k],o))}i=new CBc(true);eBc(n,new dCc);cBc(n,new $Bc('Help',i));j=XLb(a.b);for(k=0;k<j.length;++k){cBc(i,new ZBc(j[k],o))}DKc(n.db,P2c,L9c);zBc(n,L9c);return n}
var L9c='cwMenuBar',D9c='cwMenuBarEditOptions',E9c='cwMenuBarFileOptions',H9c='cwMenuBarFileRecents',I9c='cwMenuBarGWTOptions',J9c='cwMenuBarHelpOptions',K9c='cwMenuBarPrompts';YHb(749,1,{},S_b);_.rc=function T_b(){Poc(this.c[this.b]);this.b=(this.b+1)%this.c.length};_.b=0;_.d=null;YHb(750,1,Z1c);_.pc=function X_b(){BKb(this.c,O_b(this.b))};YHb(1143,104,m1c,BBc);YHb(1150,105,{100:1,105:1,119:1},ZBc,$Bc,aCc);YHb(1151,105,{100:1,106:1,119:1},dCc);var fwb=lSc(g8c,'CwMenuBar$1',749),SBb=lSc(e8c,'MenuItemSeparator',1151);M2c(xm)(7);