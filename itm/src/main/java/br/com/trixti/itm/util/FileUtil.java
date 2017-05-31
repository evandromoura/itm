package br.com.trixti.itm.util;
	import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
	
	
	/**
	 *
	 * <p>
	 * DEFINIÇÃO DA CLASSE
20	 * </p>
21	 *
22	 * <p>
23	 * OBJETIVO/PROPÓSITO
24	 * </p>
25	 *
26	 * <p>
27	 * EXEMPLO:
28	 * </p>
29	 *
30	 * @author <a href="http://gilmatryx.googlepages.com">Gilmar P.S.L.</a>
31	 *
32	 * @since
33	 *
34	 * @version
35	 */
	       
	public class FileUtil {
	
	        public static final String ENTER = "\r\n";
	
	
	       
	        /**
44	         * <p>
45	         *  Método responsável pela leitura de qualqer arquivo. Cada linha do arquivo
46	         * corresponde a um item da lista retornada. Caso tenha lido algum arquivo
47	         * ou não houve poblema na leitura, retorna List senão null
48	         * </p>
49	         *
50	         * @param pathName
51	         * @return sucess
52	         *
53	         * @since
54	         */
	               
	        public static List<String> read(String pathName) {
	
	                if (pathName != null) {
	
	                        try {
	
	                                return readFree(new File(pathName));
	
	                        } catch (FileNotFoundException e) {
	                                e.printStackTrace();
	                        } catch (IOException e) {
	                                e.printStackTrace();
	                        }
	                }
	
	                return null;
	        }
	       
	        /**
75	         * <p>
76	         * Método responável pela leitura de qualqer arquivo. Cada linha do arquivo
77	         * corresponde a um item da lista retornada. Caso tenha lido algum arquivo
78	         * ou não houve poblema na leitura, retorna List senão null.
79	         * </p>
80	         *
81	         * @param file
82	         * @return
83	         *
84	         * @since
85	         */
	               
	        public static List<String> read(File file) {
	
	                if (file != null) {
	
	                        try {
	
	                                return readFree(file);
	
	                        } catch (FileNotFoundException e) {
	                                e.printStackTrace();
	                        } catch (IOException e) {
	                                e.printStackTrace();
	                        }
	                }
	
	                return null;
	        }
	
	        private static List<String> readFree(File file) throws IOException {
	
	                List<String> archive = new ArrayList<String>();
	
	                BufferedReader reader = new BufferedReader(new FileReader(file));
	
	                String s;
	
	                do {
	                        s = reader.readLine();
	                        if (s != null) {
	                                archive.add(s);
	                        }
	                } while (s != null);
	
	                reader.close();
	
	                return archive;
	        }
	
	        public static void createOthers(File file, String tagName, int lineBuffer)
	                        throws IOException {
	
	                List<String> archive = new ArrayList<String>(lineBuffer);
	
	                BufferedReader reader = new BufferedReader(new FileReader(file));
	
	                String s;
	
	                int count = 0;
	                int countFile = 1;
	               
	                String path = StringUtils.substringBefore(file.getAbsolutePath(), "\\"
	                                + file.getName())
	                                + "\\";
	
	                do {
	
	                        s = reader.readLine();
	
	                        if (s != null) {
	                                archive.add(s + "\r\n");
	                                count++;
	                        }
	
	                        if ((count % lineBuffer == 0) && count > 1) { // multiplos
	
	                                createFile(path + tagName + countFile + ".txt", archive);
	                                archive = new ArrayList<String>(lineBuffer);
	                                countFile++;
	                        }
	
	                } while (s != null);
	
	                if (!archive.isEmpty()) {
	                        createFile(path + tagName + countFile + ".txt", archive);
	                }
	
	                reader.close();
	        }
	
	       
	       
	        /**
169	         * <p>
170	         * Método responsável pela marcação de qualqer arquivo.
171	         * </p>
172	         *
173	         * @param pathName
174	         * @param tag
175	         * @return sucess
176	         *
177	         * @since
178	         */
	               
	        public static boolean mark(String pathName, String tag) {
	
	                if (pathName != null & tag != null) {
	
	                        File arch = new File(pathName);
	                        File newArch = new File(pathName + tag);
	
	                        arch.renameTo(newArch);
	                       
	                        return true;
	                }
	                return false;
	        }
	
	
	       
	        /**
197	         * <p>
198	         * Método responsável pela renomeação de qualqer arquivo
199	         * </p>
200	         *
201	         * @param pathDir
202	         * @param name
203	         * @param wishName
204	         * @return sucess
205	         *
206	         * @since
207	         */
	               
	        public static boolean renameAs(String pathDir, String name,
	                        String wishName) {
	
	                if (pathDir != null & name != null & wishName != null) {
	
	                        File arch = new File(pathDir + "/" + name);
	                        File newArch = new File(pathDir + "/" + wishName);
	
	                        arch.renameTo(newArch);
	                       
	                        return true;
	                }
	
	                return false;
	        }
	
	       
	       
	        /**
228	         * <p>
229	         * Cria um arquivo a partir de uma única string com o layout da mesma. Ou
230	         * seja, se a string tem quebra de linha o arquivo também terá.
231	         * </p>
232	         *
233	         * @param path
234	         * @param content
235	         * @return sucess
236	         *
237	         * @since
238	         */
	               
	        public static boolean createFile(String path, String content) {
	
	                try {
	
	                        File arch = null;
	
	                        arch = new File(path);
	
	                        BufferedWriter wtr = new BufferedWriter(new FileWriter(arch));
	                        wtr.write(content);
	                        wtr.flush();
	                        wtr.close();
	
	                        arch.setWritable(true);
	                        arch.setReadable(true);
	                        arch.setExecutable(true);
	
	                        return true;
	
	                } catch (FileNotFoundException e) {
	                        e.printStackTrace();
	                } catch (IOException e) {
	                        e.printStackTrace();
	                }
	
	                return false;
	
	        }
	
	       
	        /**
271	         * <p>
272	         * Cria um arquivo com várias linhas a partir de uma lista de strings. Para
273	         * cada item da lista uma linha será criada no arquivo.
274	         * </p>
275	         *
276	         * @param path
277	         * @param content
278	         *
279	         * @since
280	         */
	               
	        public static void createFile(String path, List<String> content) {
	
	                File arq = null;
	
	                arq = new File(path);
	
	                write(arq, content);
	
	        }
	
	       
	       
	        /**
295	         * <p>
296	         *  Cria um arquivo com vária linhas a partir de uma lista de strings. Para
297	         * cada item da lista uma linha será criada no arquivo.
298	         * </p>
299	         *
300	         * @param file
301	         * @param content
302	         *
303	         * @since
304	         */
	               
	        public static void write(File file, List<String> content) {
	
	                try {
	
	                        BufferedWriter wtr = new BufferedWriter(new FileWriter(file));
	                        for (String c : content) {
	                                wtr.write(c);
	                        }
	
	                        wtr.flush();
	                        wtr.close();
	
	                } catch (FileNotFoundException e) {
	                        e.printStackTrace();
	                } catch (IOException e) {
	                        e.printStackTrace();
	                }
	        }
	
	       
	        /**
327	         * <p>
328	         * Cria um arquivo a partir de uma única string.
329	         * </p>
330	         *
331	         * @param file
332	         * @param content
333	         *
334	         * @since
335	         */
	               
	        public static void write(File file, String content) {
	
	                try {
	
	                        BufferedWriter wtr = new BufferedWriter(new FileWriter(file));
	
	                        wtr.write(content);
	
	                        wtr.flush();
	                        wtr.close();
	
	                } catch (FileNotFoundException e) {
	                        e.printStackTrace();
	                } catch (IOException e) {
	                        e.printStackTrace();
	                }
	        }
	
	}