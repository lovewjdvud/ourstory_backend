package com.backend.ourstory.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository  extends JpaRepository<Board, Integer>  {

}
