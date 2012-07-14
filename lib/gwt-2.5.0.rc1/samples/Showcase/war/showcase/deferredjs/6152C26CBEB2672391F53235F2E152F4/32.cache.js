function NE(){NE=L0c;ME=new o$c}
function OE(d,a){var b=d.a;for(var c in b){b.hasOwnProperty(c)&&a.me(c)}}
function byc(a,b,c,d){var e;a.a.Wg(b,c);e=dyc(a.a.i,b,c);Th(e,d,true)}
function SE(){NE();var a;a=dkb(ME.he(Zbd),61);if(!a){a=new RE;ME.je(Zbd,a)}return a}
function PE(c,b){try{typeof $wnd[b]!='object'&&UE(b);c.a=$wnd[b]}catch(a){UE(b)}}
function UE(a){throw new p_c(g4c+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function QE(d,a){a=String(a);var b=d.a;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.Qd(a);return String(c)}
function RE(){this.b='Dictionary userInfo';PE(this,Zbd);if(!this.a){throw new p_c("Cannot find JavaScript object with the name 'userInfo'")}}
function WZb(){var a,b,c,d,e,f,g,i,j,k,n;f=new PKc;g=new tvc('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.cb.dir=C3c;g.cb.style['textAlign']=W3c;MKc(f,new tvc('<b>This example interacts with the following JavaScript variable:<\/b>'));MKc(f,g);j=new Vxc;b=j.j;i=SE();e=(n=new w$c,OE(i,n),n);a=0;for(d=sXc(jM(e.a));d.a.se();){c=dkb(yXc(d),1);k=QE(i,c);Mxc(j,0,a,c);byc(b,0,a,'cw-DictionaryExample-header');Mxc(j,1,a,k);byc(b,1,a,'cw-DictionaryExample-data');++a}MKc(f,new tvc('<br><br>'));MKc(f,j);return f}
var Zbd='userInfo';tHb(365,1,{61:1},RE);_.Qd=function TE(a){var b;b="Cannot find '"+a+"' in "+this;throw new p_c(b)};_.tS=function VE(){return this.b};_.a=null;_.b=null;var ME;tHb(726,1,F1c);_.kc=function a$b(){bKb(this.a,WZb())};var Lpb=VRc(U7c,p5c,365);s2c(jm)(32);