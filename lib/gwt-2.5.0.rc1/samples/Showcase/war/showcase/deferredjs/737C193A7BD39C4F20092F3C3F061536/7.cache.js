function VAc(){WAc.call(this,false)}
function rBc(a,b){tBc.call(this,a,false);this.b=b}
function sBc(a,b){tBc.call(this,a,false);qBc(this,b)}
function uBc(a){tBc.call(this,'GWT',true);qBc(this,a)}
function h_b(a){this.c=a;this.b=nLb(this.c.a)}
function zAc(a,b){return GAc(a,b,a.a.b)}
function zd(a,b){Xb((ge(),be),a,Kjb(bGb,x0c,137,[(gRc(),b?fRc:eRc)]))}
function qBc(a,b){a.d=b;!!a.c&&UAc(a.c,a);if(b){b.cb.tabIndex=-1;se();zd(a.cb,true)}else{se();zd(a.cb,false)}}
function GAc(a,b,c){if(c<0||c>a.a.b){throw new ZQc}a.o&&(b.cb[k8c]=2,undefined);yAc(a,c,b.cb);xXc(a.a,c,b);return b}
function jLb(a){var b,c;b=Ujb(a.a.he(I8c),151);if(b==null){c=Kjb(gGb,y0c,1,['New','Open',J8c,K8c,'Exit']);a.a.je(I8c,c);return c}else{return b}}
function iLb(a){var b,c;b=Ujb(a.a.he(H8c),151);if(b==null){c=Kjb(gGb,y0c,1,['Undo','Redo','Cut','Copy','Paste']);a.a.je(H8c,c);return c}else{return b}}
function mLb(a){var b,c;b=Ujb(a.a.he(N8c),151);if(b==null){c=Kjb(gGb,y0c,1,['Contents','Fortune Cookie','About GWT']);a.a.je(N8c,c);return c}else{return b}}
function lLb(a){var b,c;b=Ujb(a.a.he(M8c),151);if(b==null){c=Kjb(gGb,y0c,1,['Download','Examples',Q4c,"GWT wit' the program"]);a.a.je(M8c,c);return c}else{return b}}
function kLb(a){var b,c;b=Ujb(a.a.he(L8c),151);if(b==null){c=Kjb(gGb,y0c,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.a.je(L8c,c);return c}else{return b}}
function xBc(){var a;Eh(this,$doc.createElement(p6c));this.cb[c2c]='gwt-MenuItemSeparator';a=$doc.createElement(i2c);tnc(this.cb,a);a[c2c]='menuSeparatorInner'}
function nLb(a){var b,c;b=Ujb(a.a.he(O8c),151);if(b==null){c=Kjb(gGb,y0c,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.a.je(O8c,c);return c}else{return b}}
function d_b(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new h_b(a);n=new VAc;n.b=true;n.cb.style[d2c]='500px';n.e=true;q=new WAc(true);p=kLb(a.a);for(k=0;k<p.length;++k){xAc(q,new rBc(p[k],o))}d=new WAc(true);d.e=true;xAc(n,new sBc('File',d));e=jLb(a.a);for(k=0;k<e.length;++k){if(k==3){zAc(d,new xBc);xAc(d,new sBc(e[3],q));zAc(d,new xBc)}else{xAc(d,new rBc(e[k],o))}}b=new WAc(true);xAc(n,new sBc('Edit',b));c=iLb(a.a);for(k=0;k<c.length;++k){xAc(b,new rBc(c[k],o))}f=new WAc(true);xAc(n,new uBc(f));g=lLb(a.a);for(k=0;k<g.length;++k){xAc(f,new rBc(g[k],o))}i=new WAc(true);zAc(n,new xBc);xAc(n,new sBc('Help',i));j=mLb(a.a);for(k=0;k<j.length;++k){xAc(i,new rBc(j[k],o))}_Jc(n.cb,a2c,P8c);TAc(n,P8c);return n}
var P8c='cwMenuBar',H8c='cwMenuBarEditOptions',I8c='cwMenuBarFileOptions',L8c='cwMenuBarFileRecents',M8c='cwMenuBarGWTOptions',N8c='cwMenuBarHelpOptions',O8c='cwMenuBarPrompts';iHb(747,1,{},h_b);_.mc=function i_b(){ioc(this.b[this.a]);this.a=(this.a+1)%this.b.length};_.a=0;_.c=null;iHb(748,1,l1c);_.kc=function m_b(){SJb(this.b,d_b(this.a))};iHb(1144,102,A0c,VAc);iHb(1151,103,{101:1,106:1,120:1},rBc,sBc,uBc);iHb(1152,103,{101:1,107:1,120:1},xBc);var svb=BRc(j7c,'CwMenuBar$1',747),eBb=BRc(h7c,'MenuItemSeparator',1152);$1c(km)(7);