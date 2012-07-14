function Sw(){}
function Zw(){}
function qx(){}
function ui(a,b){hi(b,a)}
function Rw(a,b){Luc(b.a,a)}
function Yw(a,b){Muc(b.a,a)}
function px(a,b){Nuc(b.a,a)}
function p4b(a){this.a=a}
function w4b(a){this.a=a}
function wvc(a){this.a=a}
function dvc(a){this.a=a}
function Tuc(a){a.f=false;Xnc(a.cb)}
function Vuc(){Wuc.call(this,new uvc)}
function Luc(a,b){Ruc(a,(a.a,Xv(b)),Yv(b))}
function Muc(a,b){Suc(a,(a.a,Xv(b)),Yv(b))}
function Nuc(a,b){Tuc(a,(a.a,Xv(b),Yv(b)))}
function Uuc(a){!a.g&&(a.g=Goc(new dvc(a)));Yi(a)}
function Qw(){Qw=L0c;Pw=new jw(Z5c,new Sw)}
function Xw(){Xw=L0c;Ww=new jw(q6c,new Zw)}
function ox(){ox=L0c;nx=new jw(Q5c,new qx)}
function uvc(){rvc.call(this);this.cb[w2c]='Caption'}
function Ouc(a){if(a.g){fRc(a.g.a);a.g=null}Mi(a,false)}
function Ruc(a,b,c){if(!Rnc){a.f=true;Ync(a.cb);a.d=b;a.e=c}}
function grc(a,b,c){var d;d=frc(a,b);!!d&&(d[L6c]=c.a,undefined)}
function Euc(a,b){var c,d;d=hpc(a.b,b);c=d.children[1];return gq(c)}
function Quc(a,b){rKc(a.cb,u2c,b);Ah(a.a,b+'-caption');rKc(Euc(a.j,1),b,n9c)}
function Puc(a,b){var c;c=b.srcElement;if(dq(c)){return Aq(iq(Euc(a.j,0)),c)}return false}
function Xv(a){var b,c;b=a.b;if(b){return c=a.a,(c.clientX||0)-Dq(b)+Fq(b)+Qq(b.ownerDocument)}return a.a.clientX||0}
function Yv(a){var b,c;b=a.b;if(b){return c=a.a,(c.clientY||0)-Eq(b)+(b.scrollTop||0)+Rq(b.ownerDocument)}return a.a.clientY||0}
function Suc(a,b,c){var d,e;if(a.f){d=b+Dq(a.cb);e=c+Eq(a.cb);if(d<a.b||d>=a.i||e<a.c){return}Ti(a,d-a.d,e-a.e)}}
function Si(a){a.w=true;if(!a.s){a.s=nq($doc,D2c);a.s.className='gwt-PopupPanelGlass';a.s.style[T3c]=(tt(),U3c);a.s.style[W3c]=0+(qu(),k3c);a.s.style[X3c]=Y3c}}
function Wuc(a){var b,c;_tc.call(this,false,true,C9c);fi(a);this.a=a;c=Euc(this.j,0);Snc(c,this.a.cb);ui(this,this.a);iq(gq(this.cb))[w2c]='gwt-DialogBox';this.i=Nq($doc);this.b=Kq($doc);this.c=Lq($doc);b=new wvc(this);$h(this,b,(Qw(),Qw(),Pw));$h(this,b,(ox(),ox(),nx));$h(this,b,(Xw(),Xw(),Ww));$h(this,b,(ix(),ix(),hx));$h(this,b,(cx(),cx(),bx))}
function l4b(){var a,b,c,d,e,f,g,i,j,k,n;a=(g=new Vuc,Quc(g,'cwDialogBox'),kvc(g.a,'Sample DialogBox'),i=new PKc,i.e[N6c]=4,zi(g.j,i),Ni(g),j=new tvc('This is an example of a standard dialog box component.'),MKc(i,j),grc(i,j,($yc(),Uyc)),k=new _mc((jMb(),$Lb)),MKc(i,k),grc(i,k,Uyc),n=new drc(d9c,new w4b(g)),MKc(i,n),eF(),grc(i,n,Zyc),g);Si(a);a.v=true;e=new drc('Show Dialog Box',new p4b(a));d=new tvc('<br><br><br>This list box demonstrates that you can drag the popup over it. This obscure corner case renders incorrectly for many other libraries.');c=new HAc;c.cb.size=1;for(b=10;b>0;--b){DAc(c,B9c+b,B9c+b,-1)}f=new PKc;f.e[N6c]=8;MKc(f,e);MKc(f,d);MKc(f,c);return f}
var B9c='item ';tHb(290,278,{},Sw);_.wc=function Tw(a){Rw(this,dkb(a,38))};_.zc=function Uw(){return Pw};var Pw;tHb(291,278,{},Zw);_.wc=function $w(a){Yw(this,dkb(a,39))};_.zc=function _w(){return Ww};var Ww;tHb(294,278,{},qx);_.wc=function rx(a){px(this,dkb(a,42))};_.zc=function sx(){return nx};var nx;tHb(823,1,C1c,p4b);_.Cc=function q4b(a){Ji(this.a);Uuc(this.a)};_.a=null;tHb(824,1,F1c);_.kc=function u4b(){bKb(this.a,l4b())};tHb(825,1,C1c,w4b);_.Cc=function x4b(a){Ouc(this.a)};_.a=null;tHb(1088,1084,W0c,Vuc);_.Kb=function Xuc(){try{ci(this.j)}finally{ci(this.a)}};_.Lb=function Yuc(){try{ei(this.j)}finally{ei(this.a)}};_.$b=function Zuc(){Ouc(this)};_.Pb=function $uc(a){switch(_oc(a.type)){case 4:case 8:case 64:case 16:case 32:if(!this.f&&!Puc(this,a)){return}}di(this,a)};_.Db=function _uc(a){Quc(this,a)};_._b=function avc(a){var b;b=a.d;!a.a&&_oc(a.d.type)==4&&Puc(this,b)&&sq(b);a.c&&(a.d,false)&&(a.a=true)};_.ac=function bvc(){Uuc(this)};_.a=null;_.b=0;_.c=0;_.d=0;_.e=0;_.f=false;_.g=null;_.i=0;tHb(1089,1,N1c,dvc);_.Jc=function evc(a){this.a.i=a.a};_.a=null;tHb(1090,1091,U0c,uvc);tHb(1094,1,{38:1,39:1,40:1,41:1,42:1,54:1},wvc);_.Fc=function xvc(a){};_.Gc=function yvc(a){};_.a=null;var Cwb=VRc(H7c,'CwDialogBox$1',823),Ewb=VRc(H7c,'CwDialogBox$3',825),nAb=VRc(D7c,'DialogBox',1088),lAb=VRc(D7c,'DialogBox$CaptionImpl',1090),mAb=VRc(D7c,'DialogBox$MouseHandler',1094),kAb=VRc(D7c,'DialogBox$1',1089),Aob=VRc(b8c,'MouseDownEvent',290),Fob=VRc(b8c,'MouseUpEvent',294),Cob=VRc(b8c,'MouseMoveEvent',291);s2c(jm)(15);