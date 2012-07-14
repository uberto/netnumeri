function Sw(){}
function Zw(){}
function qx(){}
function ui(a,b){hi(b,a)}
function Rw(a,b){zPb(b.a,a)}
function Yw(a,b){APb(b.a,a)}
function px(a,b){BPb(b.a,a)}
function dpb(a){this.a=a}
function kpb(a){this.a=a}
function kQb(a){this.a=a}
function TPb(a){this.a=a}
function HPb(a){a.f=false;LIb(a.cb)}
function JPb(){KPb.call(this,new iQb)}
function zPb(a,b){FPb(a,(a.a,Xv(b)),Yv(b))}
function APb(a,b){GPb(a,(a.a,Xv(b)),Yv(b))}
function BPb(a,b){HPb(a,(a.a,Xv(b),Yv(b)))}
function IPb(a){!a.g&&(a.g=uJb(new TPb(a)));Yi(a)}
function Qw(){Qw=zlc;Pw=new jw(nqc,new Sw)}
function Xw(){Xw=zlc;Ww=new jw(Gqc,new Zw)}
function ox(){ox=zlc;nx=new jw(eqc,new qx)}
function iQb(){fQb.call(this);this.cb[knc]='Caption'}
function CPb(a){if(a.g){V9b(a.g.a);a.g=null}Mi(a,false)}
function FPb(a,b,c){if(!FIb){a.f=true;MIb(a.cb);a.d=b;a.e=c}}
function WLb(a,b,c){var d;d=VLb(a,b);!!d&&(d[_qc]=c.a,undefined)}
function sPb(a,b){var c,d;d=XJb(a.b,b);c=d.children[1];return gq(c)}
function EPb(a,b){f3b(a.cb,inc,b);Ah(a.a,b+'-caption');f3b(sPb(a.j,1),b,Dtc)}
function DPb(a,b){var c;c=b.srcElement;if(dq(c)){return Aq(iq(sPb(a.j,0)),c)}return false}
function Xv(a){var b,c;b=a.b;if(b){return c=a.a,(c.clientX||0)-Dq(b)+Fq(b)+Qq(b.ownerDocument)}return a.a.clientX||0}
function Yv(a){var b,c;b=a.b;if(b){return c=a.a,(c.clientY||0)-Eq(b)+(b.scrollTop||0)+Rq(b.ownerDocument)}return a.a.clientY||0}
function GPb(a,b,c){var d,e;if(a.f){d=b+Dq(a.cb);e=c+Eq(a.cb);if(d<a.b||d>=a.i||e<a.c){return}Ti(a,d-a.d,e-a.e)}}
function Si(a){a.w=true;if(!a.s){a.s=nq($doc,rnc);a.s.className='gwt-PopupPanelGlass';a.s.style[Goc]=(tt(),Hoc);a.s.style[Joc]=0+(qu(),$nc);a.s.style[Koc]=Loc}}
function KPb(a){var b,c;POb.call(this,false,true,Stc);fi(a);this.a=a;c=sPb(this.j,0);GIb(c,this.a.cb);ui(this,this.a);iq(gq(this.cb))[knc]='gwt-DialogBox';this.i=Nq($doc);this.b=Kq($doc);this.c=Lq($doc);b=new kQb(this);$h(this,b,(Qw(),Qw(),Pw));$h(this,b,(ox(),ox(),nx));$h(this,b,(Xw(),Xw(),Ww));$h(this,b,(ix(),ix(),hx));$h(this,b,(cx(),cx(),bx))}
function _ob(){var a,b,c,d,e,f,g,i,j,k,n;a=(g=new JPb,EPb(g,'cwDialogBox'),$Pb(g.a,'Sample DialogBox'),i=new D3b,i.e[brc]=4,zi(g.j,i),Ni(g),j=new hQb('This is an example of a standard dialog box component.'),A3b(i,j),WLb(i,j,(OTb(),ITb)),k=new PHb((Z4(),O4)),A3b(i,k),WLb(i,k,ITb),n=new TLb(ttc,new kpb(g)),A3b(i,n),LC(),WLb(i,n,NTb),g);Si(a);a.v=true;e=new TLb('Show Dialog Box',new dpb(a));d=new hQb('<br><br><br>This list box demonstrates that you can drag the popup over it. This obscure corner case renders incorrectly for many other libraries.');c=new vVb;c.cb.size=1;for(b=10;b>0;--b){rVb(c,Rtc+b,Rtc+b,-1)}f=new D3b;f.e[brc]=8;A3b(f,e);A3b(f,d);A3b(f,c);return f}
var Rtc='item ';h0(290,278,{},Sw);_.wc=function Tw(a){Rw(this,yG(a,38))};_.zc=function Uw(){return Pw};var Pw;h0(291,278,{},Zw);_.wc=function $w(a){Yw(this,yG(a,39))};_.zc=function _w(){return Ww};var Ww;h0(294,278,{},qx);_.wc=function rx(a){px(this,yG(a,42))};_.zc=function sx(){return nx};var nx;h0(736,1,qmc,dpb);_.Cc=function epb(a){Ji(this.a);IPb(this.a)};_.a=null;h0(737,1,tmc);_.kc=function ipb(){R2(this.a,_ob())};h0(738,1,qmc,kpb);_.Cc=function lpb(a){CPb(this.a)};_.a=null;h0(1001,997,Klc,JPb);_.Kb=function LPb(){try{ci(this.j)}finally{ci(this.a)}};_.Lb=function MPb(){try{ei(this.j)}finally{ei(this.a)}};_.$b=function NPb(){CPb(this)};_.Pb=function OPb(a){switch(PJb(a.type)){case 4:case 8:case 64:case 16:case 32:if(!this.f&&!DPb(this,a)){return}}di(this,a)};_.Db=function PPb(a){EPb(this,a)};_._b=function QPb(a){var b;b=a.d;!a.a&&PJb(a.d.type)==4&&DPb(this,b)&&sq(b);a.c&&(a.d,false)&&(a.a=true)};_.ac=function RPb(){IPb(this)};_.a=null;_.b=0;_.c=0;_.d=0;_.e=0;_.f=false;_.g=null;_.i=0;h0(1002,1,Bmc,TPb);_.Jc=function UPb(a){this.a.i=a.a};_.a=null;h0(1003,1004,Ilc,iQb);h0(1007,1,{38:1,39:1,40:1,41:1,42:1,54:1},kQb);_.Fc=function lQb(a){};_.Gc=function mQb(a){};_.a=null;var qR=Jac(Xrc,'CwDialogBox$1',736),sR=Jac(Xrc,'CwDialogBox$3',738),bV=Jac(Trc,'DialogBox',1001),_U=Jac(Trc,'DialogBox$CaptionImpl',1003),aV=Jac(Trc,'DialogBox$MouseHandler',1007),$U=Jac(Trc,'DialogBox$1',1002),VK=Jac(rsc,'MouseDownEvent',290),$K=Jac(rsc,'MouseUpEvent',294),XK=Jac(rsc,'MouseMoveEvent',291);gnc(jm)(15);