package tomskasu.sancor.service;

import org.springframework.stereotype.Service;

@Service
public class WorkerService {

    public String getNormalColor(String indicator, float value) {

        return "blue";
    }

}
