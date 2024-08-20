package lma.repository;

import jakarta.transaction.Transactional;
import lma.entity.TokenPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenPairRepository extends JpaRepository<TokenPair, Long> {

    @Modifying
    @Transactional
    void deleteByRefreshToken(String refreshToken);

    TokenPair findByRefreshTokenAndUser_Id(String refreshToken, Long userId);
}
