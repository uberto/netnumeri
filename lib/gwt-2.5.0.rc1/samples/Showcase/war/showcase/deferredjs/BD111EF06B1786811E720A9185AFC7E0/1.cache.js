function $Lb(a){var b,c;b=Akb(a.b.pe(O8c),150);if(b==null){c=qkb(WGb,k1c,1,[P8c,Q8c,R8c,S8c]);a.b.re(O8c,c);return c}else{return b}}
function _Lb(a){var b,c;b=Akb(a.b.pe(T8c),150);if(b==null){c=qkb(WGb,k1c,1,[U8c,V8c,W8c,X8c,Y8c,Z8c]);a.b.re(T8c,c);return c}else{return b}}
function G7b(a){var b,c,d,e,f,g,i;i=new _Kc;YKc(i,new Gvc('<b>Select your favorite color:<\/b>'));c=$Lb(a.b);for(d=0;d<c.length;++d){b=c[d];e=new XEc(G5c,b);wrc(e,'cwRadioButton-color-'+b);d==2&&(e.d.disabled=true,Uh(e,_h(e.db)+V5c,true));YKc(i,e)}YKc(i,new Gvc('<br><b>Select your favorite sport:<\/b>'));g=_Lb(a.b);for(d=0;d<g.length;++d){f=g[d];e=new XEc('sport',f);wrc(e,'cwRadioButton-sport-'+wTc(f,q3c,P2c));d==2&&xrc(e,(SRc(),SRc(),RRc));YKc(i,e)}return i}
var O8c='cwRadioButtonColors',T8c='cwRadioButtonSports';YHb(877,1,Z1c);_.pc=function M7b(){BKb(this.c,G7b(this.b))};M2c(xm)(1);