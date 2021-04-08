/*
Desafio: Leonardo é um nômade digital e viaja pelo mundo programando em diferentes cafés das cidades por onde passa. Recentemente, resolveu criar um blog, para compartilhar suas experiências e aprendizados com seus amigos.
Para criação do blog, ele optou por utilizar uma ferramenta pronta, que há um limite de caracteres que se pode escrever por dia, e Leonardo está preocupado que essa limitação, afinal, irá impedir de contar suas melhores experiências. Para contornar esse problema, decidiu usar um sistema de abreviação de palavras em seus posts.
O sistema de abreviações é simples e funciona da seguinte forma: para cada letra, é possível escolher uma palavra que inicia com tal letra e que aparece no post. Uma vez escolhida a palavra, sempre que ela aparecer no post, ela será substituída por sua letra inicial e um ponto, diminuindo assim o número de caracteres impressos na tela.
Por exemplo, na frase: “hoje eu programei em Python”, podemos escolher a palavra “programei” para representar a letra ‘p', e a frase ficará assim: “hoje eu p. em Python”, economizando assim sete caracteres. Uma mesma palavra pode aparecer mais de uma vez no texto, e será abreviada todas as vezes. Note que, se após uma abreviação o número de caracteres não diminuir, ela não deve ser usada, tal como no caso da palavra “eu” acima.
Leonardo precisa que seu post tenha o menor número de caracteres possíveis, e por isso pediu a sua ajuda. Para cada letra, escolha uma palavra, de modo que ao serem aplicadas todas as abreviações, o texto contenha o menor número de caracteres possíveis.

Entrada: Haverá diversos casos de teste. Cada caso de teste é composto de uma linha, contendo uma frase de até 10⁴ caracteres. A frase é composta de palavras e espaços em branco, e cada palavra é composta de letras minúsculas ('a'-'z'), e contém entre 1 e 30 caracteres cada.
O último caso de teste é indicado quando a linha dada conter apenas um “.”, o qual não deverá ser processado.

Saída: Para cada caso de teste, imprima uma linha contendo a frase já com as abreviações escolhidas e aplicadas.
Em seguida, imprima um inteiro N, indicando o número de palavras em que foram escolhidas uma letra para a abreviação no texto. Nas próximas N linhas, imprima o seguinte padrão “C. = P”, onde C é a letra inicial e P é a palavra escolhida para tal letra. As linhas devem ser impressas em ordem crescente da letra inicial.
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Blog {

    public static void main(String[] args) throws IOException {
        BufferedReader inReader;
        inReader = new BufferedReader(new InputStreamReader(System.in));
        boolean leia = true;
        String line = inReader.readLine();

        while (!line.equals(".")) {
            abreviaPalavrasDaFrase(Arrays.asList(line.toLowerCase(Locale.ROOT).split(" ")));
            line = inReader.readLine();
            if (line.equals(".")) {
                System.exit(0);
            }
        }
    }

    public static void abreviaPalavrasDaFrase(List<String> frase) throws IOException {
    // processa a frase se a linha conter 1000 ou menos caracter
        // if (isFraseMenorIgualHa1000Caracter(frase)) {
        processaFrase(frase);
    //}
    }

    private static void processaFrase(List<String> fraseOriginal) {
        Set<String> listIniciaisValidas = getListIniciaisDasPalavras(fraseOriginal);
        List<String> listPalavrasHaSeremAbrevidas = new ArrayList<>();
        String palavra = "";
        int cnt = 0;

        // para todas as iniciais das palavras que foram informadas
        for (String inicial : listIniciaisValidas) {
            palavra = getPalavraHaSerAbreviada(fraseOriginal, inicial);
            listPalavrasHaSeremAbrevidas.add(palavra);
            cnt++;
        }
        System.out.println(abreviaFrase(fraseOriginal, listPalavrasHaSeremAbrevidas));
        System.out.println(listPalavrasHaSeremAbrevidas.size()); // qtd de palavras alteradas
        legendaDasPalavrasAbreviadas(listPalavrasHaSeremAbrevidas);
    }

    private static void legendaDasPalavrasAbreviadas(List<String> listPalavrasHaSeremAbrevidas) {
        Collections.sort(listPalavrasHaSeremAbrevidas);
        listPalavrasHaSeremAbrevidas.stream().map(s -> s.substring(0, 1).concat(". = ").concat(s)).collect(Collectors.toList()).forEach(System.out::println);
    }

    private static String abreviaFrase(List<String> fraseOriginal, List<String> listPalavrasHaSeremAbrevidas) {
        for (int i = 0; i < listPalavrasHaSeremAbrevidas.size(); i++) {
            for (int j = 0; j < fraseOriginal.size(); j++) {
                if (listPalavrasHaSeremAbrevidas.get(i).equals(fraseOriginal.get(j))) {
                    String palavra = listPalavrasHaSeremAbrevidas.get(i);
                    fraseOriginal.set(j, palavra.substring(0, 1).concat("."));
                }
            }
        }
        return fraseOriginal.stream().collect(Collectors.joining(" "));
    }

    private static String getPalavraHaSerAbreviada(List<String> ListaDePalavras, String letraDaAbrevicao) {
        List<String> listPalavrasIniciadasComX = getListPalavrasValidas(ListaDePalavras, letraDaAbrevicao);
        List<String> maiorPalavra = getMaiorPalavraDaLista(listPalavrasIniciadasComX);
        List<List<String>> palavrasRepetidasMaiorQueDoisCaracter = new ArrayList<>();
        List<String> todasPalavrasRepetidas = new ArrayList<>();

        // remove as palavras repetidas e cria a lista de palavras repetidas maior que dois caracter
        for (String palavra : listPalavrasIniciadasComX.stream().collect(Collectors.toSet())) {
            todasPalavrasRepetidas = getListPalavrasRepetidas(listPalavrasIniciadasComX, palavra);
            if (todasPalavrasRepetidas.size() >= 2) {
                palavrasRepetidasMaiorQueDoisCaracter.add(todasPalavrasRepetidas); //= Collections.singletonList(todasPalavrasRepetidas);
            }
        }

        // HasMap das palavras repetidas e sua economia no texto
        Map<String, Integer> palavraHeEconomia = new HashMap<String, Integer>();
        for (List<String> list : palavrasRepetidasMaiorQueDoisCaracter) {
            String palavra = list.get(0);
            palavraHeEconomia.put(palavra, list.stream().map(s -> s.substring(2)).collect(Collectors.joining()).length());
        }

        int economiaPalavraRepetida = 0;
        String palavraRepetidaMaiorEconomia = "";
        // obtem a palavra repetida de maior economia no texto
        for (String key : palavraHeEconomia.keySet()) {
            int valor = palavraHeEconomia.get(key);

            if (valor > economiaPalavraRepetida) {
                economiaPalavraRepetida = valor;
                palavraRepetidaMaiorEconomia = key.toString();
            }
        }

        // economia da maior palavra no texto
        int economiaMaiorPalavra = maiorPalavra.get(0).toString().substring(2).length();

        String palavraHaSerAbreviada = "";
        // A palavra a ser abreviada é que retornar maior economia no texto
        if (economiaMaiorPalavra > economiaPalavraRepetida) {
            palavraHaSerAbreviada = maiorPalavra.get(0).toString();
        } else {
            palavraHaSerAbreviada = palavraRepetidaMaiorEconomia;
        }
        return palavraHaSerAbreviada;
    }

    private static List getMaiorPalavraDaLista(List<String> todasPalavrasIniciadasComX) {
        return todasPalavrasIniciadasComX.stream().max(Comparator.comparingInt(String::length)).stream().collect(Collectors.toList());
    }

    private static List getListPalavrasRepetidas(List<String> listPalavras, String palavra) {
        return listPalavras.stream().filter(s -> s.equals(palavra)).collect(Collectors.toList());
    }

// retorna somente as palavras iniciadas com a letra X e maior que dois caracter e menor que 30
    private static List<String> getListPalavrasValidas(List<String> palavras, String letra) {
        return palavras.stream().filter(s -> s.startsWith(letra)).filter(s -> (s.length() > 2 && s.length() <= 30)) // retorna somente palavras maior que dois caracter e menor que ou igual a 30
                .collect(Collectors.toList());
    }

    private static Set<String> getListIniciaisDasPalavras(List<String> listPalavras) {
        Set<String> listaDasIniciais = new HashSet<String>();
        for (char letra : getLetrasDoAfabeto()) {
            if (!listPalavras.stream().filter(s -> s.startsWith(String.valueOf(letra))).filter(s -> (s.length() > 2 && s.length() <= 30)).collect(Collectors.toSet()).isEmpty()) {
                listaDasIniciais.add(String.valueOf(letra));
            }
        }
        return listaDasIniciais;
    }

    private static boolean isFraseMenorIgualHa1000Caracter(List<String> listaDePalavras) {
        return listaDePalavras.stream().collect(Collectors.joining(",")).length() <= 1000;
    }

    private static char[] getLetrasDoAfabeto() {
        return IntStream.rangeClosed('a', 'z').mapToObj(c -> " " + (char) c).collect(Collectors.joining()).toCharArray();
    }
}
