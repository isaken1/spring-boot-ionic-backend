package com.isaackennedy.curso.resources;

import com.isaackennedy.curso.domain.Produto;
import com.isaackennedy.curso.dto.ProdutoDTO;
import com.isaackennedy.curso.resources.utils.URL;
import com.isaackennedy.curso.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto cat = service.find(id);
		return ResponseEntity.ok().body(cat);
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		String decodedName = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(decodedName, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(ProdutoDTO::new);
		return ResponseEntity.ok().body(listDTO);
	}
}
