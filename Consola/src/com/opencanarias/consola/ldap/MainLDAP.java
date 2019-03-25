package com.opencanarias.consola.ldap;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MainLDAP {

	public static void main(String[] args) {
		int i = 0;
		switch (i) {
		case 0:
			listOfUsers();
			break;
		case 1:
			listOfAlfrescoGroups();
			break;
		case 2:
			listOfHierarchy();
			break;
		case 3:
			listOfHierarchyXML();
			break;
		case 4:
			listOfHierarchyJSON();
			break;
		}
	}

	private static void listOfHierarchy() {
		LDAPManager ldap = new LDAPManager("ALF","PRO.","C:\\DESA_JDK7_WF9\\AppWildfly\\config\\consola\\config.properties");
		List<LDAPGruposAlfrescoBean> lista = null;
		try {
			lista = ldap.findLDAPAlfrescoGroups("(cn>=ALF)");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<LDAPGruposAlfrescoBean> listHierarchy = new ArrayList<LDAPGruposAlfrescoBean>();
		List<LDAPGruposAlfrescoBean> listHierarchyRest = new ArrayList<LDAPGruposAlfrescoBean>();
		for (LDAPGruposAlfrescoBean map : lista) {
			int cont = 0;
			if (null!=map.getMember()&&!map.getMember().isEmpty())
				for (String alf : map.getMember()) {
					if (alf.contains("ALF"))
						cont++;
				}
			if (cont==0)
				listHierarchy.add(map);
			else
				listHierarchyRest.add(map);

		}
		System.out.println("RAIZ");
		for (LDAPGruposAlfrescoBean map : listHierarchy) {
			System.out.println("  ╚>"+map.getCn());		
			for (LDAPGruposAlfrescoBean map2 : listHierarchyRest) {
				int cont2 = 0;
				for (String alf : map2.getMember()) {
					if (alf.contains(map.getCn()))
						cont2++;
				}
				if (cont2>0) {
					System.out.println("  ║  ╚═>"+map2.getCn());
					for (LDAPGruposAlfrescoBean map3 : listHierarchyRest) {
						int cont3 = 0;
						for (String alf : map3.getMember()) {
							if (alf.contains(map2.getCn()))
								cont3++;
						}
						if (cont3>0) {
							System.out.println("  ║  ║  ╚═>"+map3.getCn());
							for (LDAPGruposAlfrescoBean map4 : listHierarchyRest) {
								int cont4 = 0;
								for (String alf : map4.getMember()) {
									if (alf.contains(map3.getCn()))
										cont4++;
								}
								if (cont4>0) {
									System.out.println("  ║  ║  ║  ╚═>"+map4.getCn());
									for (LDAPGruposAlfrescoBean map5 : listHierarchyRest) {
										int cont5 = 0;
										for (String alf : map5.getMember()) {
											if (alf.contains(map4.getCn()))
												cont5++;
										}
										if (cont5>0) {
											System.out.println("  ║  ║  ║  ║           ╚═>"+map5.getCn());

										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static void listOfHierarchyXML() {
		int level1=1;
		int level = 1;
		LDAPManager ldap = new LDAPManager("ALF","PRO.","C:\\DESA_JDK7_JSF2\\AppWildfly\\config\\consola\\config.properties");
		List<LDAPGruposAlfrescoBean> lista = null;
		try {
			lista = ldap.findLDAPAlfrescoGroups("(cn>=ALF)");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<LDAPGruposAlfrescoBean> listHierarchy = new ArrayList<LDAPGruposAlfrescoBean>();
		List<LDAPGruposAlfrescoBean> listHierarchyRest = new ArrayList<LDAPGruposAlfrescoBean>();
		for (LDAPGruposAlfrescoBean map : lista) {
			int cont = 0;
			if (null!=map.getMember()&&!map.getMember().isEmpty())
				for (String alf : map.getMember()) {
					if (alf.contains("ALF"))
						cont++;
				}
			if (cont==0)
				listHierarchy.add(map);
			else
				listHierarchyRest.add(map);

		}
		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
		System.out.println("<apsct>");
		for (LDAPGruposAlfrescoBean map : listHierarchy) {
			System.out.println("  <dpt id='"+level+"' title='"+map.getName().substring(4, map.getName().length())+"'>");
			System.out.println("    <id>"+ level1+"</id>");
			System.out.println("    <name>"+ map.getCn()+"</name>");
			int level2=1;
			for (LDAPGruposAlfrescoBean map2 : listHierarchyRest) {
				int cont2 = 0;
				for (String alf : map2.getMember()) {
					if (alf.contains(map.getCn()))
						cont2++;
				}
				if (cont2>0) {
					System.out.println("      <dpt id='"+level+"' title='"+map2.getName().substring(4, map2.getName().length())+"'>");
					System.out.println("        <id>"+ level2+"</id>");
					System.out.println("        <name>"+ map2.getCn()+"</name>");
					int level3=1;
					for (LDAPGruposAlfrescoBean map3 : listHierarchyRest) {
						int cont3 = 0;
						for (String alf : map3.getMember()) {
							if (alf.contains(map2.getCn()))
								cont3++;
						}
						if (cont3>0) {
							System.out.println("          <dpt id='"+level+"' title='"+map3.getName().substring(4, map3.getName().length())+"'>");
							System.out.println("            <id>"+ level3+"</id>");
							System.out.println("            <name>"+ map3.getCn()+"</name>");
							int level4 = 1;
							for (LDAPGruposAlfrescoBean map4 : listHierarchyRest) {
								int cont4 = 0;
								for (String alf : map4.getMember()) {
									if (alf.contains(map3.getCn()))
										cont4++;
								}
								if (cont4>0) {
									System.out.println("            <dpt id='"+level+"' title='"+map4.getName().substring(4, map4.getName().length())+"'>");
									System.out.println("              <id>"+ level4+"</id>");
									System.out.println("              <name>"+ map4.getCn()+"</name>");
									int level5 = 1;
									for (LDAPGruposAlfrescoBean map5 : listHierarchyRest) {
										int cont5 = 0;
										for (String alf : map5.getMember()) {
											if (alf.contains(map4.getCn()))
												cont5++;
										}
										if (cont5>0) {
											System.out.println("              <dpt id='"+level+"' title='"+map5.getName().substring(4, map5.getName().length())+"'>");
											System.out.println("                <id>"+ level5+"</id>");
											System.out.println("                <name>"+ map5.getCn()+"</name>");
											level5++;
											level++;
											System.out.println("              </dpt>");
										}
										
									}
									level4++;
									level++;
									System.out.println("            </dpt>");
								}
							}
							level3++;
							level++;
							System.out.println("          </dpt>");
						}
					}
				level2++;
				level++;
				System.out.println("      </dpt>");
				}
			}
			level1++;
			level++;
			System.out.println("  </dpt>");
		}
		System.out.println("</apsct>");
	}

	private static void listOfHierarchyJSON() {
		int level1=1;
		int level = 1;
		LDAPManager ldap = new LDAPManager("ALF","PRO.","C:\\DESA_JDK7_JSF2\\AppWildfly\\config\\consola\\config.properties");
		List<LDAPGruposAlfrescoBean> lista = null;
		try {
			lista = ldap.findLDAPAlfrescoGroups("(cn>=ALF)");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<LDAPGruposAlfrescoBean> listHierarchy = new ArrayList<LDAPGruposAlfrescoBean>();
		List<LDAPGruposAlfrescoBean> listHierarchyRest = new ArrayList<LDAPGruposAlfrescoBean>();
		for (LDAPGruposAlfrescoBean map : lista) {
			int cont = 0;
			if (null!=map.getMember()&&!map.getMember().isEmpty())
				for (String alf : map.getMember()) {
					if (alf.contains("ALF"))
						cont++;
				}
			if (cont==0)
				listHierarchy.add(map);
			else
				listHierarchyRest.add(map);

		}
		System.out.println("{ \"class\": \"go.TreeModel\",\"nodeDataArray\": [");
		System.out.println("{\"key\":"+level+", \"name\":\"APSCT\", \"title\":\"A. P. S/C Tenerife\"},");
		level1 = level;
		for (LDAPGruposAlfrescoBean map : listHierarchy) {
			level++;
			System.out.println("{\"key\":"+level+", \"name\":\""+map.getName().substring(4, map.getName().length())+"\", \"title\":\""+ map.getCn()+"\", \"parent\":"+level1+"},");
			
			int level2=level;
			for (LDAPGruposAlfrescoBean map2 : listHierarchyRest) {
				int cont2 = 0;
				for (String alf : map2.getMember()) {
					if (alf.contains(map.getCn()))
						cont2++;
				}
				if (cont2>0) {
					level++;
					System.out.println("{\"key\":"+level+", \"name\":\""+map2.getName().substring(4, map2.getName().length())+"\", \"title\":\""+ map2.getCn()+"\", \"parent\":"+level2+"},");
					int level3=level;
					for (LDAPGruposAlfrescoBean map3 : listHierarchyRest) {
						int cont3 = 0;
						for (String alf : map3.getMember()) {
							if (alf.contains(map2.getCn()))
								cont3++;
						}
						if (cont3>0) {
							level++;
							System.out.println("{\"key\":"+level+", \"name\":\""+map3.getName().substring(4, map3.getName().length())+"\", \"title\":\""+ map3.getCn()+"\", \"parent\":"+level3+"},");
							int level4 = level;
							for (LDAPGruposAlfrescoBean map4 : listHierarchyRest) {
								int cont4 = 0;
								for (String alf : map4.getMember()) {
									if (alf.contains(map3.getCn()))
										cont4++;
								}
								if (cont4>0) {
									level++;
									System.out.println("{\"key\":"+level+", \"name\":\""+map4.getName().substring(4, map4.getName().length())+"\", \"title\":\""+ map4.getCn()+"\", \"parent\":"+level4+"},");          
									int level5 = level;
									for (LDAPGruposAlfrescoBean map5 : listHierarchyRest) {
										int cont5 = 0;
										for (String alf : map5.getMember()) {
											if (alf.contains(map4.getCn()))
												cont5++;
										}
										if (cont5>0) {
											level++;
											System.out.println("{\"key\":"+level+", \"name\":\""+map5.getName().substring(4, map5.getName().length())+"\", \"title\":\""+ map5.getCn()+"\", \"parent\":"+level5+"},");
										}
									}
									level++;
								}
							}
							level++;
						}
					}
				level++;
				}
			}
		}
		System.out.println(" ] }");
	}

	
	private static void listOfAlfrescoGroups() {
		LDAPManager ldap = new LDAPManager("ALF","PRO.","C:\\DESA_JDK7_JSF2\\AppWildfly\\config\\consola\\config.properties");
		List<LDAPGruposAlfrescoBean> lista = null;
		try {
			lista = ldap.findLDAPAlfrescoGroups("(cn>=ALF)");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (LDAPGruposAlfrescoBean map : lista) {
			System.out.println("CN = "+map.getCn() +"\tName " +map.getName());
			System.out.println("\n-- Grupos");
			if (null!=map.getMember()&&!map.getMember().isEmpty())
				for (String dpt : map.getMember()) {
					if (dpt.contains("ALF"))
						System.out.println("\t"+dpt);
				}
			System.out.println("\t-------\n-- Usuarios");
			for (String dpt : map.getMember()) {
				if (!dpt.contains("ALF"))
					System.out.println("\t"+dpt);
			}
			System.out.println("\n");
		}
	}

	private static void listOfUsers() {
		LDAPManager ldap = new LDAPManager("USERS","PRO.","C:\\DESA_JDK7_JSF2\\AppWildfly\\config\\consola\\config.properties");
		List<LDAPPersonaBean> lista = null;
		try {
			lista = ldap.findLDAPUsers("(businessCategory>=dpt)");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (LDAPPersonaBean map : lista) {
			System.out.println(map.getCn());
			if (null!=map.getBussinessCategory()&&!map.getBussinessCategory().isEmpty())
				for (String dpt : map.getBussinessCategory()) {
					if (dpt.contains("dpt"))
						System.out.println("\t"+dpt);
				}
			if (null!=map.getMemberOf()&&!map.getMemberOf().isEmpty())
				for (String member :  map.getMemberOf()){
					if (member.contains("ALF"))
						System.out.println("\t\t"+member);
				}
		}
		System.getProperty("line.separator");
	}

}
