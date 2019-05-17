package JavaCssTranslate;

/**
 * @ author ezra
 * @ date 2019/5/17 12:21
 */
public class CssTranslate {


    public static void main(String[] args) {
        String text = "<p><strong>[物种简介]</strong><br>莕菜（学名:Nymphoides peltata (S.G. Gmel.) Kuntze），双子叶植物纲，捩花目，龙胆科，莕菜属多年生水生草本植物。<p><strong>[生长环境]</strong><br>生于池塘或不甚流动的河溪中，海拔60-1800米。<p><strong>[形态特征]</strong><br>茎圆柱形，多分枝，密生褐色斑点，节下生根。上部叶对生，下部叶互生，叶片飘浮，近革质，圆形或卵圆形，直径1.5-8厘米，基部心形，全缘，有不明显的掌状叶脉，下面紫褐色，密生腺体，粗糙，上面光滑，叶柄圆柱形，长5-10厘米，基部变宽，呈鞘状，半抱茎。花常多数，簇生节上，5数；花梗圆柱形，不等长，稍短于叶柄，长3-7厘米；花萼长9-11毫米，分裂近基部，裂片椭圆形或椭圆状披针形，先端钝，全缘；花冠金黄色，长2-3厘米，直径2.5-3厘米，分裂至近基部，冠筒短，喉部具5束长柔毛，裂片宽倒卵形，先端圆形或凹陷，中部质厚的部分卵状长圆形，边缘宽膜质，近透明，具不整齐的细条裂齿；雄蕊着生于冠筒上，整齐，花丝基部疏被长毛；在短花柱的花中，雌蕊长5-7毫米，花柱长1-2毫米，柱头小，花丝长3-4毫米，花药常弯曲，箭形，长4-6毫米；在长花柱的花中，雌蕊长7-17毫米，花柱长达10毫米，柱头大，2裂，裂片近圆形，花丝长1-2毫米，花药长2-3.5毫米；腺体5个，黄色，环绕子房基部。蒴果无柄，椭圆形，长1.7-2.5厘米，宽0.8-1.1厘米，宿存花柱长1-3毫米，成熟时不开裂；种子大，褐色，椭圆形，长4-5毫米，边缘密生睫毛。染色体2n=54，56。花果期4-10月。";
        StringBuilder stringBuilder = new StringBuilder();
        text = text.replace("<p><strong>[", "\"},{\"title\":\"");
        text = text.replace("]</strong><br>", "\",\"contents\":\"");
        text = text + "\"}]";
        text = text.substring(3);
        text = "[" + text;
        System.out.println(text);
    }

}
