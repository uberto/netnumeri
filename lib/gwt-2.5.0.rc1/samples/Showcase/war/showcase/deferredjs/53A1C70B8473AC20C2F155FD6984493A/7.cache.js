function hBc(){iBc.call(this,false)}
function FBc(a,b){HBc.call(this,a,false);this.c=b}
function GBc(a,b){HBc.call(this,a,false);EBc(this,b)}
function IBc(a){HBc.call(this,'GWT',true);EBc(this,a)}
function z_b(a){this.d=a;this.c=FLb(this.d.b)}
function NAc(a,b){return UAc(a,b,a.b.c)}
function Kd(a,b){gc((re(),me),a,dkb(yGb,M0c,136,[(uRc(),b?tRc:sRc)]))}
function EBc(a,b){a.e=b;!!a.d&&gBc(a.d,a);if(b){b.db.tabIndex=-1;De();Kd(a.db,true)}else{De();Kd(a.db,false)}}
function UAc(a,b,c){if(c<0||c>a.b.c){throw new lRc}a.p&&(b.db[J8c]=2,undefined);MAc(a,c,b.db);LXc(a.b,c,b);return b}
function BLb(a){var b,c;b=nkb(a.b.le(f9c),150);if(b==null){c=dkb(DGb,N0c,1,['New','Open',g9c,h9c,'Exit']);a.b.ne(f9c,c);return c}else{return b}}
function ALb(a){var b,c;b=nkb(a.b.le(e9c),150);if(b==null){c=dkb(DGb,N0c,1,['Undo','Redo','Cut','Copy','Paste']);a.b.ne(e9c,c);return c}else{return b}}
function ELb(a){var b,c;b=nkb(a.b.le(k9c),150);if(b==null){c=dkb(DGb,N0c,1,['Contents','Fortune Cookie','About GWT']);a.b.ne(k9c,c);return c}else{return b}}
function DLb(a){var b,c;b=nkb(a.b.le(j9c),150);if(b==null){c=dkb(DGb,N0c,1,['Download','Examples',f5c,"GWT wit' the program"]);a.b.ne(j9c,c);return c}else{return b}}
function CLb(a){var b,c;b=nkb(a.b.le(i9c),150);if(b==null){c=dkb(DGb,N0c,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.b.ne(i9c,c);return c}else{return b}}
function LBc(){var a;Ph(this,$doc.createElement(O6c));this.db[r2c]='gwt-MenuItemSeparator';a=$doc.createElement(x2c);Jnc(this.db,a);a[r2c]='menuSeparatorInner'}
function FLb(a){var b,c;b=nkb(a.b.le(l9c),150);if(b==null){c=dkb(DGb,N0c,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.b.ne(l9c,c);return c}else{return b}}
function v_b(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new z_b(a);n=new hBc;n.c=true;n.db.style[s2c]='500px';n.f=true;q=new iBc(true);p=CLb(a.b);for(k=0;k<p.length;++k){LAc(q,new FBc(p[k],o))}d=new iBc(true);d.f=true;LAc(n,new GBc('File',d));e=BLb(a.b);for(k=0;k<e.length;++k){if(k==3){NAc(d,new LBc);LAc(d,new GBc(e[3],q));NAc(d,new LBc)}else{LAc(d,new FBc(e[k],o))}}b=new iBc(true);LAc(n,new GBc('Edit',b));c=ALb(a.b);for(k=0;k<c.length;++k){LAc(b,new FBc(c[k],o))}f=new iBc(true);LAc(n,new IBc(f));g=DLb(a.b);for(k=0;k<g.length;++k){LAc(f,new FBc(g[k],o))}i=new iBc(true);NAc(n,new LBc);LAc(n,new GBc('Help',i));j=ELb(a.b);for(k=0;k<j.length;++k){LAc(i,new FBc(j[k],o))}jKc(n.db,p2c,m9c);fBc(n,m9c);return n}
var m9c='cwMenuBar',e9c='cwMenuBarEditOptions',f9c='cwMenuBarFileOptions',i9c='cwMenuBarFileRecents',j9c='cwMenuBarGWTOptions',k9c='cwMenuBarHelpOptions',l9c='cwMenuBarPrompts';FHb(745,1,{},z_b);_.rc=function A_b(){yoc(this.c[this.b]);this.b=(this.b+1)%this.c.length};_.b=0;_.d=null;FHb(746,1,A1c);_.pc=function E_b(){iKb(this.c,v_b(this.b))};FHb(1140,104,P0c,hBc);FHb(1147,105,{100:1,105:1,119:1},FBc,GBc,IBc);FHb(1148,105,{100:1,106:1,119:1},LBc);var Qvb=PRc(I7c,'CwMenuBar$1',745),BBb=PRc(G7c,'MenuItemSeparator',1148);n2c(wm)(7);