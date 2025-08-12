package com.example.daotrungtin_smilegate_testassigment.repository;

import com.example.daotrungtin_smilegate_testassigment.entity.GameName;
import com.example.daotrungtin_smilegate_testassigment.entity.GameNameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameNameRepository extends JpaRepository<GameName, GameNameId> {}