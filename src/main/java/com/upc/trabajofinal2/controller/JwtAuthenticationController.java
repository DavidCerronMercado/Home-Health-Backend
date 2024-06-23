// JwtAuthenticationController.java
package com.upc.trabajofinal2.controller;

import com.upc.trabajofinal2.entity.Medico;
import com.upc.trabajofinal2.entity.Paciente;
import com.upc.trabajofinal2.entity.Usuario;
import com.upc.trabajofinal2.security.JwtRequest;
import com.upc.trabajofinal2.security.JwtResponse;
import com.upc.trabajofinal2.security.JwtTokenUtil;
import com.upc.trabajofinal2.serviceImpl.JwtUserDetailsService;
import com.upc.trabajofinal2.serviceImpl.PacienteServiceImpl;
import com.upc.trabajofinal2.serviceImpl.UsuarioServiceImpl;
import com.upc.trabajofinal2.serviceImpl.MedicoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"}, exposedHeaders = "Authorization")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;

	@Autowired
	private PacienteServiceImpl pacienteServiceImpl;

	@Autowired
	private MedicoServiceImpl medicoServiceImpl;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		// Obtener el usuario para extraer los IDs
		Usuario usuario = usuarioServiceImpl.findByUsername(authenticationRequest.getUsername());
		Long pacienteId = null;
		Long medicoId = null;

		if (usuario != null) {
			if (usuario.getRoles().stream().anyMatch(rol -> rol.getRol().equals("Paciente"))) {
				Paciente paciente = pacienteServiceImpl.findByDni(usuario.getUsername());
				if (paciente != null) {
					pacienteId = paciente.getId();
				}
			} else if (usuario.getRoles().stream().anyMatch(rol -> rol.getRol().equals("Medico"))) {
				Medico medico = medicoServiceImpl.listarId(usuario.getId());
				if (medico != null) {
					medicoId = medico.getId();
				}
			}
		}

		final String token = jwtTokenUtil.generateToken(userDetails, usuario.getId(), pacienteId, medicoId);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "Bearer " + token);
		return ResponseEntity.ok().headers(responseHeaders).build();
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
