function Hw(){}
function Ow(){}
function fx(){}
function vi(a,b){ii(b,a)}
function Nw(a,b){iPb(b.a,a)}
function Gw(a,b){hPb(b.a,a)}
function ex(a,b){jPb(b.a,a)}
function Uob(a){this.a=a}
function _ob(a){this.a=a}
function BPb(a){this.a=a}
function UPb(a){this.a=a}
function pPb(a){a.f=false;mIb(a.cb)}
function rPb(){sPb.call(this,new SPb)}
function hPb(a,b){nPb(a,(a.a,Mv(b)),Nv(b))}
function iPb(a,b){oPb(a,(a.a,Mv(b)),Nv(b))}
function jPb(a,b){pPb(a,(a.a,Mv(b),Nv(b)))}
function qPb(a){!a.g&&(a.g=XIb(new BPb(a)));Zi(a)}
function Fw(){Fw=flc;Ew=new $v(Npc,new Hw)}
function Mw(){Mw=flc;Lw=new $v(gqc,new Ow)}
function dx(){dx=flc;cx=new $v(jqc,new fx)}
function SPb(){PPb.call(this);this.cb[Smc]='Caption'}
function kPb(a){if(a.g){B9b(a.g.a);a.g=null}Ni(a,false)}
function nPb(a,b,c){if(!gIb){a.f=true;nIb(a.cb);a.d=b;a.e=c}}
function aPb(a,b){var c,d;d=CJb(a.b,b);c=CJb(d,1);return hq(c)}
function ELb(a,b,c){var d;d=DLb(a,b);!!d&&(d[Gqc]=c.a,undefined)}
function mPb(a,b){P2b(a.cb,Qmc,b);Ah(a.a,b+'-caption');P2b(aPb(a.j,1),b,htc)}
function lPb(a,b){var c;c=b.target;if(eq(c)){return Cq(jq(aPb(a.j,0)),c)}return false}
function oPb(a,b,c){var d,e;if(a.f){d=b+uq(a.cb);e=c+(wq(a.cb)+$wnd.pageYOffset);if(d<a.b||d>=a.i||e<a.c){return}Ui(a,d-a.d,e-a.e)}}
function Mv(a){var b,c;b=a.b;if(b){return c=a.a,(c.clientX||0)-uq(b)+yq(b)+(b.ownerDocument,$wnd.pageXOffset)}return a.a.clientX||0}
function Nv(a){var b,c;b=a.b;if(b){return c=a.a,(c.clientY||0)-(wq(b)+$wnd.pageYOffset)+(b.scrollTop||0)+(b.ownerDocument,$wnd.pageYOffset)}return a.a.clientY||0}
function Ti(a){a.w=true;if(!a.s){a.s=$doc.createElement(Ymc);a.s.className='gwt-PopupPanelGlass';a.s.style[joc]=(it(),koc);a.s.style[moc]=0+(fu(),Dnc);a.s.style[noc]=ooc}}
function sPb(a){var b,c;xOb.call(this,false,true,wtc);gi(a);this.a=a;c=aPb(this.j,0);hIb(c,this.a.cb);vi(this,this.a);jq(hq(this.cb))[Smc]='gwt-DialogBox';this.i=Fq($doc);this.b=0;this.c=0;b=new UPb(this);_h(this,b,(Fw(),Fw(),Ew));_h(this,b,(dx(),dx(),cx));_h(this,b,(Mw(),Mw(),Lw));_h(this,b,(Zw(),Zw(),Yw));_h(this,b,(Tw(),Tw(),Sw))}
function Qob(){var a,b,c,d,e,f,g,i,j,k,n;a=(g=new rPb,mPb(g,'cwDialogBox'),IPb(g.a,'Sample DialogBox'),i=new l3b,i.e[Iqc]=4,Ai(g.j,i),Oi(g),j=new RPb('This is an example of a standard dialog box component.'),i3b(i,j),ELb(i,j,(wTb(),qTb)),k=new qHb((O4(),D4)),i3b(i,k),ELb(i,k,qTb),n=new BLb(Zsc,new _ob(g)),i3b(i,n),AC(),ELb(i,n,vTb),g);Ti(a);a.v=true;e=new BLb('Show Dialog Box',new Uob(a));d=new RPb('<br><br><br>This list box demonstrates that you can drag the popup over it. This obscure corner case renders incorrectly for many other libraries.');c=new dVb;c.cb.size=1;for(b=10;b>0;--b){_Ub(c,vtc+b,vtc+b,-1)}f=new l3b;f.e[Iqc]=8;i3b(f,e);i3b(f,d);i3b(f,c);return f}
var vtc='item ';Y_(291,279,{},Hw);_.wc=function Iw(a){Gw(this,nG(a,38))};_.zc=function Jw(){return Ew};var Ew;Y_(292,279,{},Ow);_.wc=function Pw(a){Nw(this,nG(a,39))};_.zc=function Qw(){return Lw};var Lw;Y_(295,279,{},fx);_.wc=function gx(a){ex(this,nG(a,42))};_.zc=function hx(){return cx};var cx;Y_(737,1,Ylc,Uob);_.Cc=function Vob(a){Ki(this.a);qPb(this.a)};_.a=null;Y_(738,1,_lc);_.kc=function Zob(){G2(this.a,Qob())};Y_(739,1,Ylc,_ob);_.Cc=function apb(a){kPb(this.a)};_.a=null;Y_(1004,1000,qlc,rPb);_.Kb=function tPb(){try{di(this.j)}finally{di(this.a)}};_.Lb=function uPb(){try{fi(this.j)}finally{fi(this.a)}};_.$b=function vPb(){kPb(this)};_.Pb=function wPb(a){switch(qJb(a.type)){case 4:case 8:case 64:case 16:case 32:if(!this.f&&!lPb(this,a)){return}}ei(this,a)};_.Db=function xPb(a){mPb(this,a)};_._b=function yPb(a){var b;b=a.d;!a.a&&qJb(a.d.type)==4&&lPb(this,b)&&(b.preventDefault(),undefined);a.c&&(a.d,false)&&(a.a=true)};_.ac=function zPb(){qPb(this)};_.a=null;_.b=0;_.c=0;_.d=0;_.e=0;_.f=false;_.g=null;_.i=0;Y_(1005,1,hmc,BPb);_.Jc=function CPb(a){this.a.i=a.a};_.a=null;Y_(1006,1007,olc,SPb);Y_(1010,1,{38:1,39:1,40:1,41:1,42:1,54:1},UPb);_.Fc=function VPb(a){};_.Gc=function WPb(a){};_.a=null;var fR=pac(Brc,'CwDialogBox$1',737),hR=pac(Brc,'CwDialogBox$3',739),SU=pac(xrc,'DialogBox',1004),QU=pac(xrc,'DialogBox$CaptionImpl',1006),RU=pac(xrc,'DialogBox$MouseHandler',1010),PU=pac(xrc,'DialogBox$1',1005),KK=pac(Xrc,'MouseDownEvent',291),PK=pac(Xrc,'MouseUpEvent',295),MK=pac(Xrc,'MouseMoveEvent',292);Omc(km)(15);