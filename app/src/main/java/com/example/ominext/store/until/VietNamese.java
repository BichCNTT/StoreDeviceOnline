package com.example.ominext.store.until;

public class VietNamese {
    private char[] charA = {'à', 'á', 'ạ', 'ả', 'ã',// 0->16
            'â', 'ầ', 'ấ', 'ậ', 'ẩ', 'ẫ', 'ă', 'ằ', 'ắ', 'ặ', 'ẳ', 'ẵ'};
    private char[] charE = {'ê', 'ề', 'ế', 'ệ', 'ể', 'ễ',// 17->27
            'è', 'é', 'ẹ', 'ẻ', 'ẽ'};// e
    private char[] charI = {'ì', 'í', 'ị', 'ỉ', 'ĩ'};// i 28->32
    private char[] charO = {'ò', 'ó', 'ọ', 'ỏ', 'õ',// o 33->49
            'ô', 'ồ', 'ố', 'ộ', 'ổ', 'ỗ',// ô
            'ơ', 'ờ', 'ớ', 'ợ', 'ở', 'ỡ'};// ơ
    private char[] charU = {'ù', 'ú', 'ụ', 'ủ', 'ũ',// u 50->60
            'ư', 'ừ', 'ứ', 'ự', 'ử', 'ữ'};// ư
    private char[] charY = {'ỳ', 'ý', 'ỵ', 'ỷ', 'ỹ'};// y 61->65
    private char[] charD = {'đ', ' '}; // 66-67
    private char[][] CH = {charA, charE, charI, charO, charU, charY, charD};
    private String charact;

    public VietNamese() {
        charact = String.valueOf(charA, 0, charA.length)
                + String.valueOf(charE, 0, charE.length)
                + String.valueOf(charI, 0, charI.length)
                + String.valueOf(charO, 0, charO.length)
                + String.valueOf(charU, 0, charU.length)
                + String.valueOf(charY, 0, charY.length)
                + String.valueOf(charD, 0, charD.length);
    }

    private char GetAlterChar(char pC) {
        if ((int) pC == 32) {
            return ' ';
        }

        char tam = pC;// Character.toLowerCase(pC);

        int i = 0;
        while (i < charact.length() && charact.charAt(i) != tam) {
            i++;
        }
        if (i < 0 || i > 67)
            return pC;

        if (i == 66) {
            return 'd';
        }
        if (i >= 0 && i <= 16) {
            return 'a';
        }
        if (i >= 17 && i <= 27) {
            return 'e';
        }
        if (i >= 28 && i <= 32) {
            return 'i';
        }
        if (i >= 33 && i <= 49) {
            return 'o';
        }
        if (i >= 50 && i <= 60) {
            return 'u';
        }
        if (i >= 61 && i <= 65) {
            return 'y';
        }
        return pC;
    }

    public String ConvertString(String pStr) {
        String convertString = pStr.toLowerCase();
        for (int i = 0; i < convertString.length(); i++) {
            char temp = convertString.charAt(i);
            if ((int) temp < 97 || temp > 122) {
                char tam1 = this.GetAlterChar(temp);
                if ((int) temp != 32)
                    convertString = convertString.replace(temp, tam1);
            }
        }
        return convertString;
    }
//    public String ConvertStringURI(String pStr) {
//        String convertString = pStr.toLowerCase();
//        Character[] returnString = new Character[convertString.length()];
//        for (int i = 0; i < convertString.length(); i++) {
//            // System.out.print(returnString[] +"-");
//            char temp = convertString.charAt(i);
//            //System.out.print(temp + "-");
//            if ((int) temp < 97 || temp > 122) {
//                char tam1 = this.GetAlterChar(temp);
//                //System.out.println(tam1 + "/");
//                if ((int) temp != 32)
//                    convertString = convertString.replace(temp, tam1);
//                else
//                    convertString = convertString.replace(temp, '+');
//            }
//        }
//        return convertString;
//    }
}