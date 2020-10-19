package OpenData;

import java.io.*;
import java.util.*;

public class infoFromCSV {
    public static void main(String[] args) {

        // 프로젝트 진행 순서
        // 1. csv파일 읽기
        // 2. 2차원배열에 저장
        // 3. 통계정보 출력

        File csv = new File("D:\\workspace\\IdeaProjects\\ev_charging_station\\한국전력공사_지역별 전기차 충전소 현황정보_20201008.csv");
        try {
            int row = 0;
            int column = 0;
            String[][] dataArray = new String[20][10];
            int[][] csvVal = new int[20][10];

            BufferedReader br = new BufferedReader(new FileReader(csv));

            String str = br.readLine();
            while (str != null) {
                String[] token = str.split(",");
                for (column = 0; column < token.length; column++) {
                    dataArray[row][column] = (token[column]);
                    String strForBlank = dataArray[row][column];
                    strForBlank = strForBlank.replaceAll("-", "0");
                    dataArray[row][column] = strForBlank;
                    //System.out.print(dataArray[row][column] + ",");
                }
                str = br.readLine();
                row++;
            }
            for (int intRow = 1; intRow < row; intRow++) { // 숫자 데이터만 csvVal배열에 저장
                for (int intColumn = 1; intColumn < column; intColumn++) {
                    int value = Integer.parseInt(dataArray[intRow][intColumn]);
                    csvVal[intRow][intColumn] = value;
                }
            }
            Map<String, Integer> map = new HashMap<>();

            for (int rowNum = 1; rowNum < row; rowNum++) {  //같은 지역 값들을 더함
                int result = 0;
                for (int columnNum = 1; columnNum < column; columnNum++) {
                    result += csvVal[rowNum][columnNum];
                }
                map.put(dataArray[rowNum][0], result);
            }

            List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() { //비교함수 Comparator를 사용
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue()); // A>B 1 리턴, A=B 0 리턴, A<B -1 리턴
                    // -1 리턴 시 앞으로 정렬
                }
            });
            System.out.println(entries);

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
