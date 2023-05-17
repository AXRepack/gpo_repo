package tomskasu.sancor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tomskasu.sancor.entity.UserEntity;
import tomskasu.sancor.entity.Worker;
import tomskasu.sancor.repo.UserEntityRepo;
import tomskasu.sancor.repo.WorkerRepo;
import tomskasu.sancor.service.UserEntityService;
import tomskasu.sancor.service.WorkerService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserEntityRepo entityRepo;
    @Autowired
    WorkerRepo workerRepo;

    @Autowired
    UserEntityService entityService;

    @Autowired
    WorkerService workerService;

    @GetMapping("{id}")
    public String showProfileByID(@PathVariable Long id, Model model) {

        Optional<Worker> worker =  workerRepo.findById(id);
        if (worker.isEmpty()) {
            model.addAttribute("response", "profile not found");
        }



        float height = worker.get().getHeight() / 100f;
        int weight = worker.get().getWeight();
        float imt = (weight / (float)(height * height));

        //float newImt = (Float.parseFloat(String.format("%.2f", imt)));
        BigDecimal bd = new BigDecimal(Float.toString(imt));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        float rounded = bd.floatValue();
        worker.get().setIMT(rounded);
        //worker.get().setTEMP();


        model.addAttribute("worker", worker.get());
        model.addAttribute("workId", worker.get().getId());

        workerRepo.save(worker.get());
        entityRepo.save(entityRepo.findById(worker.get().getId()).get());





        return "profile";
    }

    @PostMapping("editAttributes")
    public String addAttributes(Principal principal,
                                @ModelAttribute("worker") Worker worker,
                                @RequestParam Long id) {


        int age = entityService.calculateAgeFromDate(worker.getBirth());

        Optional<UserEntity> user =  entityRepo.findById(id);
        user.get().getWorker().setName(worker.getName());
        user.get().getWorker().setBirth(worker.getBirth());
        user.get().getWorker().setHeight(worker.getHeight());
        user.get().getWorker().setWeight(worker.getWeight());
        user.get().getWorker().setAge(age);
        user.get().getWorker().setPosition(worker.getPosition());
        user.get().getWorker().setSex(worker.getSex());
        user.get().getWorker().setMail(worker.getMail());
        user.get().getWorker().setIMT(worker.getIMT());
        user.get().getWorker().setCHSS(worker.getCHSS());
        user.get().getWorker().setSAD(worker.getSAD());
        user.get().getWorker().setDAD(worker.getDAD());
        user.get().getWorker().setTEMP(worker.getTEMP());
        user.get().getWorker().setSATURATION(worker.getSATURATION());
        user.get().getWorker().setTEMP_COLOR(workerService.getNormalColor("TEMP", user.get().getWorker().getTEMP()));
        user.get().getWorker().setIMT_COLOR(workerService.getNormalColor("IMT", user.get().getWorker().getIMT()));
        user.get().getWorker().setCHSS_COLOR(workerService.getNormalColor("CHSS", (float)user.get().getWorker().getCHSS()));
        user.get().getWorker().setSAD_COLOR(workerService.getNormalColor("SAD", (float)user.get().getWorker().getSAD()));
        user.get().getWorker().setDAD_COLOR(workerService.getNormalColor("DAD", (float)user.get().getWorker().getDAD()));
        user.get().getWorker().setSATURATION_COLOR(workerService.getNormalColor("SATURATION", (float)user.get().getWorker().getSATURATION()));
        workerRepo.save(user.get().getWorker());
        entityRepo.save(user.get());
        return "redirect:/profile/" + user.get().getWorker().getId();
    }


    @GetMapping("all")
    public String showAll(Model model) {
        List<Worker> workers = workerRepo.findAll();
        model.addAttribute("workers", workers);
        return "employeeList";
    }




}
