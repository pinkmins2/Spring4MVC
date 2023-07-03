package jina.hello.spring4.controller;

import jina.hello.spring4.model.Board;
import jina.hello.spring4.service.BoardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService bsrv;

    private Logger logger = LogManager.getLogger(BoardController.class);

    @GetMapping("/list")
    public String list(Model m, int cpg) {
        logger.info("board/list 호출!!");
        m.addAttribute("boards", bsrv.readBoard(cpg));
        //m.addAttribute("psnum", bsrv.readBoard(cpg)/10);    // 페이지 네이션 시작 번호
        //m.addAttribute("allpg", bsrv.readBoard(cpg)/10);    // 총 페이지 수

        return "board/list.tiles";
    }

    @GetMapping("/write")
    public String write() {

        return "board/write.tiles";
    }

    @PostMapping("/write")
    public String writeok(Board bd) {
        //logger.info("board/write 호출!!");
        String returnPage = "redirect:/board/fail";

        if(bsrv.saveBoard(bd))
            returnPage = "redirect:/board/list?cpg=1";

        return returnPage;
    }

    @GetMapping("/view")
    public String view(Model m, String bno) {
        logger.info("board/view 호출!!");
        Board board = bsrv.readOneBoard(bno);

        m.addAttribute("board", board);

        return "board/view.tiles";
    }

}
