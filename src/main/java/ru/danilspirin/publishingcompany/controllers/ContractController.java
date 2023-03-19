package ru.danilspirin.publishingcompany.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.exceptions.ContractNumberNonUniqueException;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.service.ContractService;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Controller
@RequestMapping("/contracts")
public class ContractController {

    ContractService contractService;

    @GetMapping
    public String showAllContracts(Model contracts){
        contracts.addAttribute("contracts", contractService.getAllContracts());
        return "contracts-view/contracts";
    }

    @GetMapping("/{id}")
    public String showContract(
            @PathVariable String id,
            Model model){
        Contract contract = contractService.getContract(id);
        model.addAttribute("contract", contract);
        return "contracts-view/contract";
    }

    @GetMapping("/{id}/edit")
    public String showEditContractForm(
            @PathVariable String id,
            Model model){
        Contract contract = contractService.getContract(id);
        model.addAttribute("contract", contract);
        return "contracts-view/contract_edit";
    }

    @PatchMapping("/{id}")
    public String editContract(
            @PathVariable String id,
            @ModelAttribute @Valid Contract contract,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()){
            return "contracts-view/contract_edit";
        }
        Contract replaced;
        try{
            replaced = contractService.changeContractInfo(id, contract);
        }catch (ContractNumberNonUniqueException ex){
            bindingResult.addError(
                    new FieldError("Contract", "contractNumber",
                            contract.getContractNumber(),
                            false,null,null,
                            ex.getMessage())
            );
            return "contracts-view/contract_edit";
        }
        return "redirect:/contracts/" + replaced.getId();
    }

    @DeleteMapping("/{contractNumber}")
    public String deleteContract(@PathVariable String contractNumber){
        contractService.delete(contractNumber);
        return "redirect:/contracts";
    }
}
