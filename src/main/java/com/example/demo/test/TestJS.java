package com.example.demo.test;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

class TestJS {
    public static void main(String[] args) {

        ScriptEngineManager manager = new ScriptEngineManager();
        //得到脚本引擎
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        String page = "<script>var x=\"charAt@search@@cookie@@split@div@fromCharCode@2@@@new@while@@join@var@@g@innerHTML@52@13@@@@@@@0xEDB88320@window@else@firstChild@@https@Wed@challenge@catch@RegExp@try@__cdn_clearance@@@href@Path@eval@@Km@@@2F0MHi@@replace@d@8@attachEvent@T@@@@@rOm9XFMtA3QKV7nYsPGT4lifyWwkq5vcjH2IdxUoCbhERLaz81DNB6@@@@@0xFF@setTimeout@1500@1@toLowerCase@@String@document@1543406932@@match@a@addEventListener@36@3@createElement@@for@if@@f@M2@charCodeAt@pathname@@toString@08@641@28@E@JgSe0upZ@location@Nov@Array@onreadystatechange@H@@@e@@false@substr@18@NO@@length@reverse@return@captcha@@DOMContentLoaded@@@Expires@5@@chars@parseInt@3D@@function@@GMT@0\".replace(/@*$/,\"\").split(\"@\"),\n" +
                "y=\"g 1c=2x(){1k('24.G=24.1G+24.2.15(/[\\\\?|&]2l-z/,\\\\'\\\\')',1l);1q.4='D=1r.20|2A|'+(2x(){g 2a=[2x(1c){2k 1c},2x(2a){2k 2a},(2x(){g 1c=1q.1y('7');1c.j='<1u G=\\\\'/\\\\'>1i</1u>';1c=1c.v.G;g 2a=1c.1t(/x?:\\\\/\\\\//)[2A];1c=1c.2e(2a.2i).1n();2k 2x(2a){1A(g 1i=2A;1i<2a.2i;1i++){2a[1i]=1c.1(2a[1i])};2k 2a.f('')}})(),2x(1c){2k I('1p.8('+1c+')')}],1i=[([-~[(+!+{})+(+!+{})]]*(-~[(+!+{})+(+!+{})])+[]+[]),'22',[[(-~-~!{}<<-~-~!{})]+(~~{}+[[]][2A])],[(~~{}+[[]][2A])],'19',[-~[]]+({}+[]+[[]][2A]).1([-~[]]+(~~{}+[[]][2A]))+(-~!{}+9+[]),'28%13',[[(-~-~!{}<<-~-~!{})]+[(-~[]+[(-~!{}<<(+!+{})+(+!+{}))]>>-~[])]],'1E',[!/!/+[]+[[]][2A]][2A].1(9),[[-~[2r]]+([-~[(+!+{})+(+!+{})]]*(-~[(+!+{})+(+!+{})])+[]+[]),[(-~[]+[(-~!{}<<(+!+{})+(+!+{}))]>>-~[])]+[-~-~!{}-~[(+!+{})+(+!+{})]],[-~[2r]]+[-~[2r]]],'10',[[-~[]]+[-~-~!{}]+(~~{}+[[]][2A])],'2g',(~~{}+[[]][2A]),[(-~!{}+9+[])+[(-~[]+[(-~!{}<<(+!+{})+(+!+{}))]>>-~[])]],'2v'];1A(g 1c=2A;1c<1i.2i;1c++){1i[1c]=2a[[2A,1m,1x,9,1m,2A,1m,1x,1m,2A,1x,1m,1x,1m,2A,1x,1m][1c]](1i[1c])};2k 1i.f('')})()+';2q=y, 21-25-2f l:1J:k 2z;H=/;'};1B((2x(){C{2k !!t.1v;}A(2b){2k 2d;}})()){1q.1v('2n',1c,2d)}u{1q.18('27',1c)}\",\n" +
                "f=function(x,y){\n" +
                "\tvar a=0,b=0,c=0;x=x.split(\"\");\n" +
                "\ty=y||99;\n" +
                "\twhile((a=x.shift())&&(b=a.charCodeAt(0)-77.5))c=(Math.abs(b)<13?(b+48.5):parseInt(a,36))+y*c;\n" +
                "\treturn c\n" +
                "},\n" +
                "z=f(y.match(/\\w/g).sort(function(x,y){return f(x)-f(y)}).pop());\n" +
                "while(z++)try{eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)}));break}catch(_){}\n" +
                "\n" +
                "</script> ";
        //处理加密js
        String js = page.trim().replace("<script>", "").replace("</script>", "").replace("eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)}));", "y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)})");
        System.out.println("js: " + js);
        try {
            //得到解密后的js
            String result = (String) engine.eval(js);
            System.out.println("js: " + result);
            result = result.substring(result.indexOf("var cd"), result.indexOf("dc+=cd;") + 7);
            result = result.replaceAll("document*.*toLowerCase\\(\\)", "'x'");
            System.out.println("result: " + result);
            //得到cookie
            String jsl = (String) engine.eval(result);
            System.out.println(jsl);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
