package com.lojaDeRoupas.app.service;

import com.lojaDeRoupas.app.coreClasses.genericCrudSuperClasses.CrudGenericService;
import com.lojaDeRoupas.app.dtos.dtoEntrada.ClienteDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoEntrada.FuncionarioDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoEntrada.ProdutoDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoEntrada.VendaDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoSaida.VendaDtoSaida;
import com.lojaDeRoupas.app.entities.FuncionarioEntity;
import com.lojaDeRoupas.app.entities.ProdutoEntity;
import com.lojaDeRoupas.app.entities.VendaEntity;
import com.lojaDeRoupas.app.repositories.ClienteRepository;
import com.lojaDeRoupas.app.repositories.FuncionarioRepository;
import com.lojaDeRoupas.app.repositories.VendaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Service
@Transactional
public class VendaService extends CrudGenericService<
        VendaEntity,
        VendaRepository,
        VendaDtoEntrada,
        VendaDtoSaida> {
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EntityManager entityManager;
    public VendaDtoEntrada verificarStatus(VendaDtoEntrada venda){
        if(venda.getStatusDaVenda() == null){
            throw new RuntimeException("Status da venda invalido");
        }
        if(venda.getStatusDaVenda().equals("CANCELADO")){
            venda.setProdutos(null);
            venda.setValorTotal(BigDecimal.valueOf(0.00));
        }
        return venda;
    }
    public double calcularValorTotal(List<ProdutoDtoEntrada> produtos){
        if (produtos.isEmpty()) {
            throw new IllegalArgumentException("A lista de produtos não pode estar vazia.");
        }

        double resultado = 0;
        for (ProdutoDtoEntrada produto : produtos) {
            resultado += produto.getValor().doubleValue();
        }

        return resultado;
    }

    public void validaClienteEVendedor(VendaEntity venda) {
        //Verifica se o funcionario e o cliente foram cadastrados antes da venda
        boolean funcionarioCadastrado =
                funcionarioRepository.existsByMatricula(venda.getFuncionario().getMatricula());
        boolean clienteCadastrado = clienteRepository.existsByCpf(venda.getCliente().getCpf());

        if(!clienteCadastrado){
            throw new RuntimeException("Cliente não cadastrado");
        }
        if(!funcionarioCadastrado){
            throw new RuntimeException("Funcionário não cadastrado");
        }
        //Caso o funcionario e o cliente existam no banco pegam a mesma referencia
        venda.setCliente(clienteRepository.findByCpf(venda.getCliente().getCpf()));
        venda.setFuncionario(funcionarioRepository.findByMatricula(venda.getFuncionario().getMatricula()).get());
    }

    @Override
    public String register(VendaDtoEntrada objectIn) throws Exception {

        //Seta o valor total dos produtos fazendo a soma de todos, convertendo para bigDecimal antes de salvar
        objectIn.setValorTotal(BigDecimal.valueOf(this.calcularValorTotal(objectIn.getProdutos())));

        //Chama o metodo generico para a persistencia
        return genericRegister(objectIn, VendaEntity.class);
    }

    @Override
    public List<VendaDtoSaida> find(Long quantity, String order) throws Exception {
        return genericFind(quantity, order, VendaDtoSaida.class);
    }

    @Override
    public VendaDtoSaida findById(UUID id) throws Exception {
        return genericFindById(id, VendaDtoSaida.class);
    }

    @Override
    public String update(VendaDtoEntrada objectIn) throws Exception {
        //Verifica se a compra foi cancelada
        System.out.println("Status da venda " + objectIn.getStatusDaVenda());
        verificarStatus(objectIn);

        if(objectIn.getProdutos() != null){
            //Seta o valor total dos produtos fazendo a soma de todos, convertendo para bigDecimal antes de salvar
            objectIn.setValorTotal(BigDecimal.valueOf(this.calcularValorTotal(objectIn.getProdutos())));
        }
        //Chama o metodo generico para a persistencia
        return genericUpdate(objectIn, VendaEntity.class);
    }

    @Override
    public String delete(UUID uuid) throws Exception {
        return genericDelete(uuid);
    }

    @Override
    public void verifications(VendaDtoEntrada objectIn) throws Exception {
    }

    @Override
    public void handleRelatedEntities(VendaEntity entityToPersist) {
        validaClienteEVendedor(entityToPersist);
    }

    @Override
    public void applyBusinessRules(VendaEntity entityToPersist) {
    }
}
