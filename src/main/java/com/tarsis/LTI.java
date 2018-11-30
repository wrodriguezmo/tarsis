package com.tarsis;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.imsglobal.lti.BasicLTIUtil;
import org.imsglobal.lti.launch.LtiLaunch;
import org.imsglobal.lti.launch.LtiVerificationResult;
import org.imsglobal.pox.IMSPOXRequest;

import oauth.signpost.exception.OAuthException;

import org.imsglobal.lti.launch.*;

public class LTI {
	static LtiVerifier ltiVerifier;
	static String key = "key";
	static String secret = "secret";
	/* 
	 * Valida el request https que llega de Coursera (parámetros, signatura y que no se haya procesado antes)
	 * Genera un registro del usuario, para su posterior calificación.
	*/
	
	public static void autenticar(HttpServletRequest request, String secret) {
		ltiVerifier = new LtiOauthVerifier();
		String key = request.getParameter("oauth_consumer_key");
		//Verifica que sea un request
		try {
			LtiVerificationResult ltiResult = ltiVerifier.verify(request, secret);
			if(ltiResult.getSuccess()) {
				try {
					IMSPOXRequest.sendReplaceResult(request.getParameter("lis_outcome_service_url"), key, secret, "Algo", "0.5");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OAuthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (LtiVerificationException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void registrarIngreso() {
		
	}
	

}

/*
/**
function registrarIngeso(req) {
    return new Promise(function (resolve, reject) {
        var provider = new lti.Provider(consumer_key, consumer_secret);
        provider.valid_request(req, function (err, is_valid) {
            var body = req.body;
            if (!is_valid || !provider.outcome_service) return reject(new Error("El envío de los parámetros desde Coursera no coincide."));
            if (!body.custom_examen) return reject(new Error('Es necesario indicar el id del examen en los parámetros de personalización de la actividad. Por ejemplo, llave: examen y valor: 1'));

            var actividad = body.resource_link_title;
            var nombre = body.lis_person_name_full;
            var userId = body.user_id;
            var examenId = body.custom_examen;
            var serviceUrl = body.lis_outcome_service_url;
            var sourcedId = body.lis_result_sourcedid;

            resolve(db.Examen.findById(examenId).then(function (examen) {
                if (!examen) {
                    throw new Error("El examen que intenta consultar no existe. Verifique el id del examen en los parámetros de personalización de la actividad");
                }
                return db.Estudiante.upsert({
                    id: userId,
                    nombre: nombre
                });
            }).then(function () {
                var respuestaExamen = {
                    ExamenId: examenId,
                    EstudianteId: userId,
                    lis_outcome_service_url: serviceUrl,
                    lis_result_sourcedid: sourcedId,
                    actividad: actividad
                };
                return db.Respuesta.save(respuestaExamen);
            }).then(function () {
                return {
                    userId: userId,
                    examenId: examenId
                };
            }));
        });
    });
}


function calificar(userId, examenId, nota) {


    return db.Respuesta.findOne({
        where: {
            ExamenId: examenId,
            EstudianteId: userId
        }
    }).then(function (respuesta) {

        if (nota < 0 || nota > 1) throw new Error("La nota debe ser un número entre 0 y 1");
        if (!respuesta) throw new Error("El usuario no se ha registrado para resolver el examen");

        var body = {
            lis_outcome_service_url: respuesta.lis_outcome_service_url,
            lis_result_sourcedid: respuesta.lis_result_sourcedid
        };

        return sendResultToCoursera(body, nota);
    });
}

function sendResultToCoursera(body, nota) {
    return new Promise(function (resolve, reject) {
        var provider = new lti.Provider(consumer_key, consumer_secret);
        provider.parse_request(null, body);
        provider.outcome_service.send_replace_result(nota, function (err, result) {
            if (err) return reject(err);
            resolve(result);
        });
    });
}

module.exports = {
    registrarIngeso: registrarIngeso,
    calificar: calificar
};
 */