package JavaFunctionForScan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ author ezra
 * @ date 2019/4/23 18:49
 */
public class StringTest {


	public void StringShow() {
		String contentList = "<p><strong>[Scientific Name]</strong><br>Black Shank（Phytophthora）<p><strong>[Description]</strong><br>The symptoms of this disease can be observed at all growth stages of the plant and in all plant parts. Leaves show abnormal colors and forms, distortion and curling. Necrotic patches appear amidst an extensively chlorotic leaf. As the disease progresses, the death tissue falls off and leaves take a ragged appearance. In trees, the fruits also have an abnormal shape, and their skin is covered by black or brown lesions. At later stages extensive mold develop on these lesions and ooze can be seen dripping down. Fruits wither and the skin show clear signs of shrinking. The tree bark turns dark brown and cankers are clearly visible on the woody stems. Copious gum exudes from these lesions (gummosis). When cut open, the internal tissues of stems and roots show signs of rot (discoloration). Overall, plants wilt and in severe cases, damping off can be observed.<p><strong>[Management]</strong><br>(1) Biological Control.The control of this fungus will be different depending on the crop and the environmental conditions. Many antagonist and pathogens of Phytophthora nicotianae exist, for example Aspergillus terreus, Pseudomonas putida or Trichoderma harzianum. Copper-based fungicides applied every 2-3 months during the wet season can reduce the incidence of the disease. Lesioned bark may be removed and painted with a copper fungicide paste.####(2) Chemical Control.Always consider an integrated approach with preventive measures together with biological treatmetns if available. In some crops, both metalaxyl and phosphonate have be found effective. Some resistance to metalaxyl have been reported.";
		String[] contents = contentList.split("<p>");
		StringBuffer contentbuffer = new StringBuffer();
		for (int i = 1; i < contents.length; i++) {
			contents[i] = contents[i].trim();
			String[] part = contents[i].split("<br>");
			for (String string2 : part) {
				StringBuffer stringBuffer = new StringBuffer();
				string2 = string2.replace("<strong>[", "");
				string2 = string2.replace("]</strong>", "");
				stringBuffer.append(string2);
				contentbuffer.append(stringBuffer);
			}
		}
		System.out.println(contentbuffer);
	}

	public static void StringDetail() {
		String content = "<p><strong>[Scientific Name]</strong><br>Ranunculus chinensis Bunge<p><strong>[Commom Name]</strong><br>Hui hui suan<p><strong>[Introduction]</strong><br>Herbs perennial or annual. Flowering Apr–Sep.<p><strong>[Morphological Characteristics]</strong><br>Roots fibrous, subequally thick. Stems 10–50 cm, densely hispid, simple or branched. Basal leaves several; petiole 4–20 cm, hispid; blade ternate, ovate in outline, 4–8 × 4–10.5 cm, papery, strigose; central leaflet stalked, rhombic or broadly rhombic, 3-partite, secondary lobes rhombic-cuneate, 2- or 3-lobed; lateral leaflets smaller, shortly stalked, oblique flabellate, unequally 2-partite. Lower stem leaves similar to basal ones, upper stem leaves smaller, shortly petiolate, 3-sect. Compound monochasium terminal, 3- to several flowered; bracts leaflike. Flowers 0.7–1.2 cm in diam. Pedicel 0.5–2 cm, strigose. Receptacle densely puberulent. Sepals 5, reflexed, elliptic-ovate, 3–5 mm, abaxially strigose. Petals 5, obovate or ovate, 5–6 × 2.8–3 mm, nectary pit covered by a scale, apex rounded. Stamens numerous; anthers oblong. Aggregate fruit ovoid-cylindric or ovoid, 6–10 × 4–6 mm; carpels numerous.Achene bilaterally compressed, obliquely obovate, 2–2.5 × 1.6–2 mm, glabrous, narrowly marginate; style persistent, ca. 0.2 mm.<p><strong>[Distribution]</strong><br>Bhutan, China, N India, Japan, Kazakhstan, Korea, Mongolia, N Pakistan, Russia (Siberia), Thailand.";
		String[] contents = content.split("<p>");
		ArrayList<Map<String, StringBuffer>> contentlist = new ArrayList<>();
		for (int i = 1; i < contents.length; i++) {
			contents[i] = contents[i].trim();
			String[] part = contents[i].split("<br>");
			Map<String, StringBuffer> conntentmap = new HashMap<>();
			for (String string2 : part) {
				StringBuffer stringBuffer = new StringBuffer();
				string2 = string2.replace("<strong>[", "");
				string2 = string2.replace("]</strong>", "：");
				stringBuffer.append(string2);
				if (conntentmap.containsKey("title")) {
					conntentmap.put("content", stringBuffer);
				} else {
					conntentmap.put("title", stringBuffer);
				}
			}
			contentlist.add(conntentmap);
		}
		System.out.println(contentlist);
	}



	public static void main(String[] args) throws ParseException {

		String currentMonth = 2019 + "-"+ 1 + "-1";
		String nextMonth = 2019 + "-"+ 2 + "-1";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse(currentMonth);
		Date nextDate = simpleDateFormat.parse(nextMonth);
		long ts = date.getTime()/1000;
		long _ts = nextDate.getTime()/1000;
		String time ;
		String nextTime;
		time = String.valueOf(ts);
		nextTime = String.valueOf(_ts);
		System.out.println(time+ "++++"+nextTime);
	}
}