/*
Encontre a maior substring comum entre as duas strings informadas. A substring pode ser qualquer parte da string, inclusive ela toda. Se não houver subseqüência comum, a saída deve ser “0”. A comparação é case sensitive ('x' != 'X').

Entrada: A entrada contém vários casos de teste. Cada caso de teste é composto por duas linhas, cada uma contendo uma string. Ambas strings de entrada contém entre 1 e 50 caracteres ('A'-'Z','a'-'z' ou espaço ' '), inclusive, ou no mínimo uma letra ('A'-'Z','a'-'z').

Saída: O tamanho da maior subsequência comum entre as duas Strings.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Substring {

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        var str1 = in.readLine(); // usado em uma outra ide para aceitar o var strl1, por ser uma feature nova
        while (str1 != null && !str1.isEmpty() && str1.length() <= 50) { 
            var str2 = in.readLine();

            if (str2.length() > 50 || str2.isEmpty()) {
                break;
            }

            int maxSubstring = 0, lengthSubstring = 0;
            StringBuilder subString = new StringBuilder();
            for (var i = 0; i < str1.length(); i++) {
                for (var j = i; j < str1.length(); j++) {
                    subString.append(str1.charAt(j));

                    if (str2.contains(subString)) {
                        lengthSubstring++;

                        if (lengthSubstring > maxSubstring) {
                            maxSubstring = lengthSubstring;
                        }
                    } else {
                        subString.setLength(0);
                        if (lengthSubstring > maxSubstring) {
                            maxSubstring = lengthSubstring;
                        }

                        lengthSubstring = 0;

                        break;
                    }
                }
            }

            System.out.println(maxSubstring);
            str1 = in.readLine();
        }
        out.close();
    }

}
