package com.phillit.qa.recommendword.Common;

public class SeparateKorean {
    /* **********************************************
     * 자음 모음 분리
     * 설연수 -> ㅅㅓㄹㅇㅕㄴㅅㅜ,    바보 -> ㅂㅏㅂㅗ
     * **********************************************/
    /** 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ) */
    private char[] arrChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
            0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
            0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
    /** 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)*/
    /** ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ */
    private char[] arrJungSung = { 0x314f, 0x3150, 0x3151, 0x3152,
            0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a,
            0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162,
            0x3163 };
    /** 종성 - 가(없음), 갈(ㄹ) 천(ㄴ) */
    /** ㄱㄲ*/
    private char[] arrJongSung = { 0x0000, 0x3131, 0x3132, 0x3133,
            0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c,
            0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145,
            0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };


    /* **********************************************
     * 알파벳으로 변환
     * 설연수 -> tjfdustn, 멍충 -> ajdcnd
     * **********************************************/
    /** 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ) */
    private String[] arrChoSungEng = { "r", "R", "s", "e", "E",
            "f", "a", "q", "Q", "t", "T", "d", "w",
            "W", "c", "z", "x", "v", "g" };

    /** 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)*/
    private String[] arrJungSungEng = { "k", "o", "i", "O",
            "j", "p", "u", "P", "h", "hk", "ho", "hl",
            "y", "n", "nj", "np", "nl", "b", "m", "ml",
            "l" };

    /** 종성 - 가(없음), 갈(ㄹ) 천(ㄴ) */
    private String[] arrJongSungEng = { "", "r", "R", "rt",
            "s", "sw", "sg", "e", "f", "fr", "fa", "fq",
            "ft", "fx", "fv", "fg", "a", "q", "qt", "t",
            "T", "d", "w", "c", "z", "x", "v", "g" };

    /** 단일 자음 - ㄱ,ㄴ,ㄷ,ㄹ... (ㄸ,ㅃ,ㅉ은 단일자음(초성)으로 쓰이지만 단일자음으론 안쓰임) */
    private String[] arrSingleJaumEng = { "r", "R", "rt",
            "s", "sw", "sg", "e","E" ,"f", "fr", "fa", "fq",
            "ft", "fx", "fv", "fg", "a", "q","Q", "qt", "t",
            "T", "d", "w", "W", "c", "z", "x", "v", "g" };

    public String Convert(String args){

        String word 		= args;		// 분리할 단어
        String result		= "";									// 결과 저장할 변수
        String resultEng	= "";									// 알파벳으로

        for (int i = 0; i < word.length(); i++) {

            /*  한글자씩 읽어들인다. */
            char chars = (char) (word.charAt(i) - 0xAC00);

            if (chars >= 0 && chars <= 11172) {
                /* A. 자음과 모음이 합쳐진 글자인경우 */

                /* A-1. 초/중/종성 분리 */
                int chosung 	= chars / (21 * 28);
                int jungsung 	= chars % (21 * 28) / 28;
                int jongsung 	= chars % (21 * 28) % 28;

                //System.out.println("초성:"+chosung + " /중성:"+jungsung + " /종성:"+jongsung);



                /* A-2. result에 담기 */
                //result = result + arrChoSung[chosung] + arrJungSung[jungsung];
                result = result + arrChoSung[chosung] + separate_jungsung(arrJungSung[jungsung]);

                /* 자음분리 */
                if (jongsung != 0x0000) {
                    /* A-3. 종성이 존재할경우 result에 담는다 */
                    result =  result + separate_jongsung(arrJongSung[jongsung]);
                }

                /* 알파벳으로 */
                resultEng = resultEng + arrChoSungEng[chosung] + arrJungSungEng[jungsung];
                if (jongsung != 0x0000) {
                    /* A-3. 종성이 존재할경우 result에 담는다 */
                    resultEng =  resultEng + arrJongSungEng[jongsung];
                }

            } else {
                /* B. 한글이 아니거나 자음만 있을경우 */

                /* 자음분리 */
                result = result + ((char)(chars + 0xAC00));

                /* 알파벳으로 */
                if( chars>=34097 && chars<=34126){
                    /* 단일자음인 경우 */
                    int jaum 	= (chars-34097);
                    resultEng = resultEng + arrSingleJaumEng[jaum];
                } else if( chars>=34127 && chars<=34147) {
                    /* 단일모음인 경우 */
                    int moum 	= (chars-34127);
                    resultEng = resultEng + arrJungSungEng[moum];
                } else {
                    /* 알파벳인 경우 */
                    resultEng = resultEng + ((char)(chars + 0xAC00));
                }
            }//if

        }//for

        //System.out.println("============ result ==========");
        //System.out.println("단어     : " + word);
        //System.out.println("자음분리 : " + result);
        //System.out.println("알파벳   : " + resultEng);

        return result;
    }

    private String separate_jongsung(char args){
        String result = "";

        // 3 ㄳ
        if(args == arrJongSung[3]){
            result = result + arrJongSung[1] + arrJongSung[19];
            // 5 ㄵ
        }else if(args == arrJongSung[5]){
            result = result + arrJongSung[4] + arrJongSung[22];
        }
        // 6 ㄶ
        else if(args == arrJongSung[6]){
            result = result + arrJongSung[4] + arrJongSung[27];
        }
        // ㄺ
        else if(args == arrJongSung[9]){
            result = result + arrJongSung[8] + arrJongSung[1];
        }
        // ㄻ
        else if(args == arrJongSung[10]){
            result = result + arrJongSung[8] + arrJongSung[16];
        }
        // ㄼ
        else if(args == arrJongSung[11]){
            result = result + arrJongSung[8] + arrJongSung[17];
        }
        // ㄽ
        else if(args == arrJongSung[12]){
            result = result + arrJongSung[8] + arrJongSung[19];
        }
        // ㄾ
        else if(args == arrJongSung[13]){
            result = result + arrJongSung[8] + arrJongSung[25];
        }
        // ㄿ
        else if(args == arrJongSung[14]){
            result = result + arrJongSung[8] + arrJongSung[26];
        }
        // ㅀ
        else if(args == arrJongSung[15]){
            result = result + arrJongSung[8] + arrJongSung[27];
        }
        // ㅄ
        else if(args == arrJongSung[18]){
            result = result + arrJongSung[17] + arrJongSung[19];
        }
        else{
            result = result + args;
        }
        return result;
    }

    private String separate_jungsung(char args){
        String result = "";

        // 10 ㅘ
        if(args == arrJungSung[9]){
            result = result + arrJungSung[8] + arrJungSung[0];
            //11 ㅙ
        }else if(args == arrJungSung[10]){
            result = result + arrJungSung[8] + arrJungSung[1];
        }
        //12 ㅚ
        else if (args == arrJungSung[11]){
            result = result + arrJungSung[8] + arrJungSung[20];
        }
        //15 ㅝ
        else if(args == arrJungSung[14]){
            result = result + arrJungSung[13] + arrJungSung[4];
        }
        //16 ㅞ
        else if(args == arrJungSung[15]){
            result = result + arrJungSung[13] + arrJungSung[5];
        }
        //17 ㅟ
        else if(args == arrJungSung[16]){
            result = result + arrJungSung[13] + arrJungSung[20];
        }
        //20 ㅢ
        else if(args == arrJungSung[19]){
            result = result + arrJungSung[18] + arrJungSung[20];
        }else{
            result = result + args;
        }
        return result;
    }
}



