function CE(){CE=r0c;BE=new WZc}
function DE(d,a){var b=d.a;for(var c in b){b.hasOwnProperty(c)&&a.me(c)}}
function Lxc(a,b,c,d){var e;a.a.Wg(b,c);e=Nxc(a.a.i,b,c);Uh(e,d,true)}
function HE(){CE();var a;a=Ujb(BE.he(Cbd),61);if(!a){a=new GE;BE.je(Cbd,a)}return a}
function EE(c,b){try{typeof $wnd[b]!='object'&&JE(b);c.a=$wnd[b]}catch(a){JE(b)}}
function JE(a){throw new X$c(M3c+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function FE(d,a){a=String(a);var b=d.a;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.Qd(a);return String(c)}
function GE(){this.b='Dictionary userInfo';EE(this,Cbd);if(!this.a){throw new X$c("Cannot find JavaScript object with the name 'userInfo'")}}
function LZb(){var a,b,c,d,e,f,g,i,j,k,n;f=new xKc;g=new bvc('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.cb.dir=f3c;g.cb.style['textAlign']=z3c;uKc(f,new bvc('<b>This example interacts with the following JavaScript variable:<\/b>'));uKc(f,g);j=new Dxc;b=j.j;i=HE();e=(n=new c$c,DE(i,n),n);a=0;for(d=$Wc($L(e.a));d.a.se();){c=Ujb(eXc(d),1);k=FE(i,c);uxc(j,0,a,c);Lxc(b,0,a,'cw-DictionaryExample-header');uxc(j,1,a,k);Lxc(b,1,a,'cw-DictionaryExample-data');++a}uKc(f,new bvc('<br><br>'));uKc(f,j);return f}
var Cbd='userInfo';iHb(366,1,{61:1},GE);_.Qd=function IE(a){var b;b="Cannot find '"+a+"' in "+this;throw new X$c(b)};_.tS=function KE(){return this.b};_.a=null;_.b=null;var BE;iHb(727,1,l1c);_.kc=function RZb(){SJb(this.a,LZb())};var Apb=BRc(y7c,V4c,366);$1c(km)(32);