package victor.training.spring.web.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "teacherBioServices", url="${teacher.bio.uri.base}/api")
interface TeacherBioFeignClient {
    @GetMapping("teachers/{teacherId}/bio")
    String registerSheep(@PathVariable("teacherId") long teacherId);
}