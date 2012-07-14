function lBc(){mBc.call(this,false)}
function JBc(a,b){LBc.call(this,a,false);this.b=b}
function KBc(a,b){LBc.call(this,a,false);IBc(this,b)}
function MBc(a){LBc.call(this,'GWT',true);IBc(this,a)}
function s_b(a){this.c=a;this.b=yLb(this.c.a)}
function RAc(a,b){return YAc(a,b,a.a.b)}
function zd(a,b){Xb((ge(),be),a,Vjb(mGb,R0c,137,[(ARc(),b?zRc:yRc)]))}
function IBc(a,b){a.d=b;!!a.c&&kBc(a.c,a);if(b){b.cb.tabIndex=-1;se();zd(a.cb,true)}else{se();zd(a.cb,false)}}
function YAc(a,b,c){if(c<0||c>a.a.b){throw new rRc}a.o&&(b.cb[G8c]=2,undefined);QAc(a,c,b.cb);RXc(a.a,c,b);return b}
function PBc(){var a;Eh(this,nq($doc,K6c));this.cb[w2c]='gwt-MenuItemSeparator';a=nq($doc,D2c);Snc(this.cb,a);a[w2c]='menuSeparatorInner'}
function uLb(a){var b,c;b=dkb(a.a.he(c9c),151);if(b==null){c=Vjb(rGb,S0c,1,['New','Open',d9c,e9c,'Exit']);a.a.je(c9c,c);return c}else{return b}}
function tLb(a){var b,c;b=dkb(a.a.he(b9c),151);if(b==null){c=Vjb(rGb,S0c,1,['Undo','Redo','Cut','Copy','Paste']);a.a.je(b9c,c);return c}else{return b}}
function xLb(a){var b,c;b=dkb(a.a.he(h9c),151);if(b==null){c=Vjb(rGb,S0c,1,['Contents','Fortune Cookie','About GWT']);a.a.je(h9c,c);return c}else{return b}}
function wLb(a){var b,c;b=dkb(a.a.he(g9c),151);if(b==null){c=Vjb(rGb,S0c,1,['Download','Examples',k5c,"GWT wit' the program"]);a.a.je(g9c,c);return c}else{return b}}
function vLb(a){var b,c;b=dkb(a.a.he(f9c),151);if(b==null){c=Vjb(rGb,S0c,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.a.je(f9c,c);return c}else{return b}}
function yLb(a){var b,c;b=dkb(a.a.he(i9c),151);if(b==null){c=Vjb(rGb,S0c,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.a.je(i9c,c);return c}else{return b}}
function o_b(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new s_b(a);n=new lBc;n.b=true;n.cb.style[x2c]='500px';n.e=true;q=new mBc(true);p=vLb(a.a);for(k=0;k<p.length;++k){PAc(q,new JBc(p[k],o))}d=new mBc(true);d.e=true;PAc(n,new KBc('File',d));e=uLb(a.a);for(k=0;k<e.length;++k){if(k==3){RAc(d,new PBc);PAc(d,new KBc(e[3],q));RAc(d,new PBc)}else{PAc(d,new JBc(e[k],o))}}b=new mBc(true);PAc(n,new KBc('Edit',b));c=tLb(a.a);for(k=0;k<c.length;++k){PAc(b,new JBc(c[k],o))}f=new mBc(true);PAc(n,new MBc(f));g=wLb(a.a);for(k=0;k<g.length;++k){PAc(f,new JBc(g[k],o))}i=new mBc(true);RAc(n,new PBc);PAc(n,new KBc('Help',i));j=xLb(a.a);for(k=0;k<j.length;++k){PAc(i,new JBc(j[k],o))}rKc(n.cb,u2c,j9c);jBc(n,j9c);return n}
var j9c='cwMenuBar',b9c='cwMenuBarEditOptions',c9c='cwMenuBarFileOptions',f9c='cwMenuBarFileRecents',g9c='cwMenuBarGWTOptions',h9c='cwMenuBarHelpOptions',i9c='cwMenuBarPrompts';tHb(746,1,{},s_b);_.mc=function t_b(){Hoc(this.b[this.a]);this.a=(this.a+1)%this.b.length};_.a=0;_.c=null;tHb(747,1,F1c);_.kc=function x_b(){bKb(this.b,o_b(this.a))};tHb(1141,102,U0c,lBc);tHb(1148,103,{101:1,106:1,120:1},JBc,KBc,MBc);tHb(1149,103,{101:1,107:1,120:1},PBc);var Dvb=VRc(F7c,'CwMenuBar$1',746),pBb=VRc(D7c,'MenuItemSeparator',1149);s2c(jm)(7);