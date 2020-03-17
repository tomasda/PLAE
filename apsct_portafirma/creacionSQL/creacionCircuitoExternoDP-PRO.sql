INSERT INTO PF_FLUJOS (ID_FLUJO, FECHA_CREACION, ID_TIPO_FLUJO, ORDEN_ACTIVO, DESCRIPCION) VALUES (34, '29/01/16', 2, 1, 'Validaci�n Responsable Dominio P�blico - Firma Director');
INSERT INTO PF_GRUPOS (ID_GRUPO, FIRMANTES_REQUERIDOS, ORDEN, ID_FLUJO, ID_TIPO_GRUPO, CERRADO) VALUES (66, 1, 1, 34, 1, 0);
INSERT INTO PF_GRUPOS (ID_GRUPO, FIRMANTES_REQUERIDOS, ORDEN, ID_FLUJO, ID_TIPO_GRUPO, CERRADO) VALUES (67, 1, 2, 34, 2, 0);
INSERT INTO PF_GRUPO_PERSONAS (ID_GRUPO_PERSONA, ID_GRUPO, ID_PERSONA, PERSONA_OBLIGATORIA, REALIZADO) VALUES (66, 66, 14, 0, 0); -- Andr�s D�maso G�mez
INSERT INTO PF_GRUPO_PERSONAS (ID_GRUPO_PERSONA, ID_GRUPO, ID_PERSONA, PERSONA_OBLIGATORIA, REALIZADO) VALUES (67, 66, 21, 0, 0); -- Gloria Antu�a
INSERT INTO PF_GRUPO_PERSONAS (ID_GRUPO_PERSONA, ID_GRUPO, ID_PERSONA, PERSONA_OBLIGATORIA, REALIZADO) VALUES (68, 67, 10, 0, 0); -- Jos� Rafael D�az
--
COMMIT;

-- Usuario DPP-EXTERNO
INSERT INTO PF_CIRCUITO_POR_PERSONA (ID_REGISTRO, ID_CIRCUITO, DNI_SOLICITANTE) VALUES (1005, 34, '28282828G');
--
-- empDomPub
INSERT INTO PF_CIRCUITO_POR_PERSONA (ID_REGISTRO, ID_CIRCUITO, DNI_SOLICITANTE) VALUES (1006, 34, '99999999R');

COMMIT;