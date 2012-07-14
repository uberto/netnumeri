function CAc(){DAc.call(this,false)}
function $Ac(a,b){aBc.call(this,a,false);this.c=b}
function _Ac(a,b){aBc.call(this,a,false);ZAc(this,b)}
function bBc(a){aBc.call(this,'GWT',true);ZAc(this,a)}
function X$b(a){this.d=a;this.c=bLb(this.d.b)}
function gAc(a,b){return nAc(a,b,a.b.c)}
function zd(a,b){Xb((ge(),be),a,Ejb(WFb,V_c,136,[(EQc(),b?DQc:CQc)]))}
function ZAc(a,b){a.e=b;!!a.d&&BAc(a.d,a);if(b){b.db.tabIndex=-1;se();zd(a.db,true)}else{se();zd(a.db,false)}}
function nAc(a,b,c){if(c<0||c>a.b.c){throw new vQc}a.p&&(b.db[M7c]=2,undefined);fAc(a,c,b.db);VWc(a.b,c,b);return b}
function ZKb(a){var b,c;b=Ojb(a.b.he(i8c),150);if(b==null){c=Ejb(_Fb,W_c,1,['New','Open',j8c,k8c,'Exit']);a.b.je(i8c,c);return c}else{return b}}
function YKb(a){var b,c;b=Ojb(a.b.he(h8c),150);if(b==null){c=Ejb(_Fb,W_c,1,['Undo','Redo','Cut','Copy','Paste']);a.b.je(h8c,c);return c}else{return b}}
function aLb(a){var b,c;b=Ojb(a.b.he(n8c),150);if(b==null){c=Ejb(_Fb,W_c,1,['Contents','Fortune Cookie','About GWT']);a.b.je(n8c,c);return c}else{return b}}
function _Kb(a){var b,c;b=Ojb(a.b.he(m8c),150);if(b==null){c=Ejb(_Fb,W_c,1,['Download','Examples',p4c,"GWT wit' the program"]);a.b.je(m8c,c);return c}else{return b}}
function $Kb(a){var b,c;b=Ojb(a.b.he(l8c),150);if(b==null){c=Ejb(_Fb,W_c,1,['Fishing in the desert.txt','How to tame a wild parrot','Idiots Guide to Emu Farms']);a.b.je(l8c,c);return c}else{return b}}
function eBc(){var a;Eh(this,$doc.createElement(S5c));this.db[A1c]='gwt-MenuItemSeparator';a=$doc.createElement(G1c);cnc(this.db,a);a[A1c]='menuSeparatorInner'}
function bLb(a){var b,c;b=Ojb(a.b.he(o8c),150);if(b==null){c=Ejb(_Fb,W_c,1,['Thank you for selecting a menu item','A fine selection indeed',"Don't you have anything better to do than select menu items?",'Try something else','this is just a menu!','Another wasted click']);a.b.je(o8c,c);return c}else{return b}}
function T$b(a){var b,c,d,e,f,g,i,j,k,n,o,p,q;o=new X$b(a);n=new CAc;n.c=true;n.db.style[B1c]='500px';n.f=true;q=new DAc(true);p=$Kb(a.b);for(k=0;k<p.length;++k){eAc(q,new $Ac(p[k],o))}d=new DAc(true);d.f=true;eAc(n,new _Ac('File',d));e=ZKb(a.b);for(k=0;k<e.length;++k){if(k==3){gAc(d,new eBc);eAc(d,new _Ac(e[3],q));gAc(d,new eBc)}else{eAc(d,new $Ac(e[k],o))}}b=new DAc(true);eAc(n,new _Ac('Edit',b));c=YKb(a.b);for(k=0;k<c.length;++k){eAc(b,new $Ac(c[k],o))}f=new DAc(true);eAc(n,new bBc(f));g=_Kb(a.b);for(k=0;k<g.length;++k){eAc(f,new $Ac(g[k],o))}i=new DAc(true);gAc(n,new eBc);eAc(n,new _Ac('Help',i));j=aLb(a.b);for(k=0;k<j.length;++k){eAc(i,new $Ac(j[k],o))}EJc(n.db,y1c,p8c);AAc(n,p8c);return n}
var p8c='cwMenuBar',h8c='cwMenuBarEditOptions',i8c='cwMenuBarFileOptions',l8c='cwMenuBarFileRecents',m8c='cwMenuBarGWTOptions',n8c='cwMenuBarHelpOptions',o8c='cwMenuBarPrompts';bHb(744,1,{},X$b);_.nc=function Y$b(){Unc(this.c[this.b]);this.b=(this.b+1)%this.c.length};_.b=0;_.d=null;bHb(745,1,J0c);_.lc=function a_b(){GJb(this.c,T$b(this.b))};bHb(1138,102,Y_c,CAc);bHb(1145,103,{100:1,105:1,119:1},$Ac,_Ac,bBc);bHb(1146,103,{100:1,106:1,119:1},eBc);var nvb=ZQc(L6c,'CwMenuBar$1',744),$Ab=ZQc(J6c,'MenuItemSeparator',1146);w1c(km)(7);