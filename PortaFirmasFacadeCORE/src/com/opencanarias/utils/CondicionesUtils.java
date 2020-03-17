package com.opencanarias.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.logging.Logger;

import es.apt.ae.facade.utils.xml.CommonBean;


public class CondicionesUtils {

	private static Logger logger = Logger.getLogger(CondicionesUtils.class);
	
	/**
	 * Evalúa si se cumple una condición sobre un conjunto de propiedades.
	 * 
	 * @param bean
	 *            Datos de entrada
	 * @param condicion
	 *            Condición a evaluar
	 * @return 'true' si se cumple la condición y 'false' en caso contrario.
	 */
	public static boolean evaluarCondicionSimple(String condicion, CommonBean bean, HashMap<String, String> propiedadesActividad) {
		HashMap<String, String> propiedades = new HashMap<String, String>();
		propiedades.putAll(propiedadesActividad);

		Boolean resultEval = false;

		if (condicion != null && !condicion.trim().equals("")) {
			List<String> logicalOperators = new ArrayList<String>();
			List<Boolean> resultsEval = new ArrayList<Boolean>();
			String caracteres = " A-Za-zÁ-Úá-ú0-9_-";
			Pattern pattern = Pattern.compile("[\t ]*\\(([" + caracteres
					+ "]+)[\t ]*([=!]=|LIKE)[\t ]*([\\']?[" + caracteres
					+ "]*[\\']?)\\)[\t ]*(?:(&&|\\|\\|)?)");
			Matcher matcher = pattern.matcher(condicion);
			while (matcher.find()) {
				String leftSide = "";
				String operator = "";
				String rightSide = "";
				try {
					resultEval = false;

					leftSide = matcher.group(1);
					if (leftSide != null) {
						leftSide = leftSide.trim();
					}
					operator = matcher.group(2);
					rightSide = matcher.group(3);
					String logicalOperator = matcher.group(4);
					if (logicalOperator != null) {
						logicalOperators.add(logicalOperator);
					}

					String propertyValue = null;
					if (propiedades.containsKey(leftSide)) {
						propertyValue = "" + propiedades.get(leftSide) + "";
					} else {
						propertyValue = "" + bean.getProperty(leftSide) + "";
					}

					String rightSideValue = "";
					if (rightSide.equalsIgnoreCase("false")
							|| rightSide.equalsIgnoreCase("true")
							|| rightSide.equalsIgnoreCase("null")) {
						rightSideValue = rightSide;
					} else if (rightSide.matches("'[" + caracteres + "]+'")) {
						rightSideValue = rightSide.substring(1,
								rightSide.length() - 1);
					} else {
						if (propiedades.containsKey(rightSide)) {
							rightSideValue = propiedades.get(rightSide);
						} else {
							rightSideValue = bean.getProperty(rightSide);
						}
					}

					if ("==".equals(operator)) {
						if (propertyValue.equalsIgnoreCase(rightSideValue)) {
							resultEval = true;
						}
					} else if ("!=".equals(operator)) {
						if (!propertyValue.equalsIgnoreCase(rightSideValue)) {
							resultEval = true;
						}
					} else if ("LIKE".equals(operator)) {
						if (propertyValue.contains(rightSideValue)) {
							resultEval = true;
						}
					}
					resultsEval.add(resultEval);
				} catch (Exception e) {
					logger.error("Se ha producido un error al evaluar la condicion " + leftSide + operator + rightSide, e);
				}
			}
			int numConditions = resultsEval.size();
			int numLogicalOp = logicalOperators.size();
			if (numConditions == (numLogicalOp + 1)) {
				resultEval = resultsEval.get(0);
				for (int i = 0; i < logicalOperators.size(); i++) {
					String logicalOp = logicalOperators.get(i);
					if (logicalOp.equals("&&")) {
						resultEval = resultEval && resultsEval.get(i + 1);
					} else { // ||
						resultEval = resultEval || resultsEval.get(i + 1);
					}
				}
			} else {
				resultEval = false;
			}
		} else {
			resultEval = true;
		}

		return resultEval;
	}

	public static boolean evaluarCondicion(String condicion, CommonBean bean, HashMap<String, String> propiedadesActividad) {
		Boolean resultEval = false;

		if (condicion != null && !condicion.trim().equals("")) {
			List<String> logicalOperators = new ArrayList<String>();
			List<Boolean> resultsEval = new ArrayList<Boolean>();
			Pattern pattern = Pattern.compile("[\t ]*\\((\\(.*?\\))\\)[\t ]*(?:(&&|\\|\\|)?)");
			Matcher matcher = pattern.matcher(condicion);
			String condicionSinEvaluar = condicion;
			while (matcher.find()) {
				String leftSide = "";
				try {
					resultEval = false;
					leftSide = matcher.group(1);
					resultEval = evaluarCondicionSimple(leftSide, bean, propiedadesActividad);
					String logicalOperator = matcher.group(2);
					if (logicalOperator != null) {
						logicalOperators.add(logicalOperator);
					}
					resultsEval.add(resultEval);
					condicionSinEvaluar = condicionSinEvaluar.substring((leftSide.length()+2 + (!StringUtils.isNullOrEmpty(logicalOperator)?logicalOperator.length():0)), 
							condicionSinEvaluar.length());
				} catch (Exception e) {
					logger.error("Se ha producido un error al procesar la condicion " + leftSide, e);
				}
			}
			if (!condicionSinEvaluar.isEmpty()) {
				resultEval = evaluarCondicionSimple(condicionSinEvaluar, bean, propiedadesActividad);
				resultsEval.add(resultEval);
			}
			int numConditions = resultsEval.size();
			int numLogicalOp = logicalOperators.size();
			if (numConditions == (numLogicalOp + 1)) {
				resultEval = resultsEval.get(0);
				for (int i = 0; i < logicalOperators.size(); i++) {
					String logicalOp = logicalOperators.get(i);
					if (logicalOp.equals("&&")) {
						resultEval = resultEval && resultsEval.get(i + 1);
					} else { // ||
						resultEval = resultEval || resultsEval.get(i + 1);
					}
				}
			} else {
				resultEval = false;
			}
		} else {
			resultEval = true;
		}

		return resultEval;
	}
	
	public static boolean evaluarCondicionSimple(String condicion, HashMap<String, String> propiedades) {
		Boolean resultEval = false;

		if (condicion != null && !condicion.trim().equals("")) {
			List<String> logicalOperators = new ArrayList<String>();
			List<Boolean> resultsEval = new ArrayList<Boolean>();
			String caracteres = " A-Za-zÁ-Úá-ú0-9_-";
			Pattern pattern = Pattern.compile("[\t ]*\\(([" + caracteres
					+ "]+)[\t ]*([=!]=|LIKE)[\t ]*([\\']?[" + caracteres
					+ "]*[\\']?)\\)[\t ]*(?:(&&|\\|\\|)?)");
			Matcher matcher = pattern.matcher(condicion);
			while (matcher.find()) {
				String leftSide = "";
				String operator = "";
				String rightSide = "";
				try {
					resultEval = false;

					leftSide = matcher.group(1);
					if (leftSide != null) {
						leftSide = leftSide.trim();
					}
					operator = matcher.group(2);
					rightSide = matcher.group(3);
					String logicalOperator = matcher.group(4);
					if (logicalOperator != null) {
						logicalOperators.add(logicalOperator);
					}

					String propertyValue = "" + propiedades.get(leftSide) + "";

					String rightSideValue = "";
					if (rightSide.equalsIgnoreCase("false")
							|| rightSide.equalsIgnoreCase("true")
							|| rightSide.equalsIgnoreCase("null")) {
						rightSideValue = rightSide;
					} else if (rightSide.matches("'[" + caracteres + "]*'")) {
						rightSideValue = rightSide.substring(1,
								rightSide.length() - 1);
					} else {
						rightSideValue = propiedades.get(rightSide);
					}

					if ("==".equals(operator)) {
						if (propertyValue.equalsIgnoreCase(rightSideValue)) {
							resultEval = true;
						}
					} else if ("!=".equals(operator)) {
						if (!propertyValue.equalsIgnoreCase(rightSideValue)) {
							resultEval = true;
						}
					} else if ("LIKE".equals(operator)) {
						if (propertyValue.contains(rightSideValue)) {
							resultEval = true;
						}
					}
					resultsEval.add(resultEval);
				} catch (Exception e) {
					logger.error("Se ha producido un error al evaluar la condicion " + leftSide + operator + rightSide, e);
				}
			}
			int numConditions = resultsEval.size();
			int numLogicalOp = logicalOperators.size();
			if (numConditions == (numLogicalOp + 1)) {
				resultEval = resultsEval.get(0);
				for (int i = 0; i < logicalOperators.size(); i++) {
					String logicalOp = logicalOperators.get(i);
					if (logicalOp.equals("&&")) {
						resultEval = resultEval && resultsEval.get(i + 1);
					} else { // ||
						resultEval = resultEval || resultsEval.get(i + 1);
					}
				}
			} else {
				resultEval = false;
			}
		} else {
			resultEval = true;
		}

		return resultEval;
	}
	
	public static boolean evaluarCondicion(String condicion, HashMap<String, String> propiedades) {
		Boolean resultEval = false;

		if (condicion != null && !condicion.trim().equals("")) {
			List<String> logicalOperators = new ArrayList<String>();
			List<Boolean> resultsEval = new ArrayList<Boolean>();
			Pattern pattern = Pattern.compile("[\t ]*\\((\\(.*?\\))\\)[\t ]*(?:(&&|\\|\\|)?)");
			Matcher matcher = pattern.matcher(condicion);
			String condicionSinEvaluar = condicion;
			while (matcher.find()) {
				String leftSide = "";
				try {
					resultEval = false;
					leftSide = matcher.group(1);
					resultEval = evaluarCondicionSimple(leftSide, propiedades);
					String logicalOperator = matcher.group(2);
					if (logicalOperator != null) {
						logicalOperators.add(logicalOperator);
					}
					resultsEval.add(resultEval);
					condicionSinEvaluar = condicionSinEvaluar.substring((leftSide.length()+2 + (!StringUtils.isNullOrEmpty(logicalOperator)?logicalOperator.length():0)), 
							condicionSinEvaluar.length());
				} catch (Exception e) {
					logger.error("Se ha producido un error al procesar la condicion " + leftSide, e);
				}
			}
			if (!condicionSinEvaluar.isEmpty()) {
				resultEval = evaluarCondicionSimple(condicionSinEvaluar, propiedades);
				resultsEval.add(resultEval);
			}
			int numConditions = resultsEval.size();
			int numLogicalOp = logicalOperators.size();
			if (numConditions == (numLogicalOp + 1)) {
				resultEval = resultsEval.get(0);
				for (int i = 0; i < logicalOperators.size(); i++) {
					String logicalOp = logicalOperators.get(i);
					if (logicalOp.equals("&&")) {
						resultEval = resultEval && resultsEval.get(i + 1);
					} else { // ||
						resultEval = resultEval || resultsEval.get(i + 1);
					}
				}
			} else {
				resultEval = false;
			}
		} else {
			resultEval = true;
		}

		return resultEval;
	}
	
	public static void main(String[] args) {
		String condicion = "((_DEC_subsanaContenido=='elaborarInforme')&&(_DEC_tipoTramitacion=='normal'))||((_DEC_subsanaContenido=='delegarElaboracionInforme')&&(_DEC_tipoTramitacion=='normal'))";
		//String condicion = "(_DEC_subsanaContenido=='subsana')||(_DEC_tipoTramitacion=='normal')";
		HashMap<String, String> propiedades = new HashMap<String, String>();
		//propiedades.put("_DEC_subsanaContenido", "elaborarInforme");
		propiedades.put("_DEC_subsanaContenido", "delegarElaboracionInforme");
		propiedades.put("_DEC_tipoTramitacion", "normal");
		System.out.println("Resultado evaluar condicion " + condicion + ": " + evaluarCondicion(condicion, propiedades));
	}
}
