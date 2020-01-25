package com.isaackennedy.curso.services;

import com.isaackennedy.curso.domain.Cliente;
import com.isaackennedy.curso.repositories.ClienteRepository;
import com.isaackennedy.curso.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Random rand = new Random();

    public String sendNewPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado!");
        }

        String newPass = newPassword();
        cliente.setSenha(passwordEncoder.encode(newPass));
        clienteRepository.save(cliente);
        return newPass;
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) {
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) {
            return (char) (rand.nextInt(26) + 65);
        } else {
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
