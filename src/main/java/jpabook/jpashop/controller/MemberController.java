package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createFrom(Model model){ // view에 model을 실어서 넘긴다.
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // valid: notEmpty 검증
    // BindingResult 검증 오류를 보관하는 객체
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // memberform이 파라미터로 넘어옴
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // 이전 페이지로 돌아감
    }

    @GetMapping("/members")
    public String list(Model model) {
        // DTO로 바꾸자
        // api를 만들때는 절대 entity를 넘기면 안된다.!!
        List<Member> members = memberService.findMember();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
