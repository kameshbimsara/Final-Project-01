package lk.Project.SmartBiz.service.impl;

import jakarta.transaction.Transactional;
import lk.Project.SmartBiz.dto.BizOwnerDto;
import lk.Project.SmartBiz.dto.BizOwnerDtoReturn;
import lk.Project.SmartBiz.dto.BizOwnerLoginRequest;
import lk.Project.SmartBiz.dto.BizOwnerLoginResponse;
import lk.Project.SmartBiz.entity.BizOwner;
import lk.Project.SmartBiz.repo.BizOwnerRepo;
import lk.Project.SmartBiz.repo.BusinessRepo;
import lk.Project.SmartBiz.service.BizOwnerService;
import lk.Project.SmartBiz.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BizOwnerServiceImpl implements BizOwnerService {

    private final BizOwnerRepo bizOwnerRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public BizOwnerServiceImpl(BizOwnerRepo bizOwnerRepo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.bizOwnerRepo = bizOwnerRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BizOwnerDtoReturn saveBizOwner(BizOwnerDto bizOwnerDto) {
        if (bizOwnerRepo.findByEmail(bizOwnerDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already taken");
        }else if (bizOwnerRepo.findByNicNumber(bizOwnerDto.getNicNumber()).isPresent()) {
            throw new RuntimeException("NIC Number already taken");
        }

        String encoded = passwordEncoder.encode(bizOwnerDto.getPassword());

        BizOwner bizOwner = new BizOwner(null, bizOwnerDto.getName(), bizOwnerDto.getEmail(),encoded,bizOwnerDto.getNicNumber(),null);
        BizOwner save = bizOwnerRepo.save(bizOwner);

        return new BizOwnerDtoReturn(save.getId(), save.getName(), save.getEmail(),save.getNicNumber());
    }

    @Override
    public BizOwnerDtoReturn updateBizOwner(BizOwnerDto bizOwnerDto) {
        BizOwner bizOwner = bizOwnerRepo.findById(bizOwnerDto.getId())
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        bizOwner.setName(bizOwnerDto.getName());
        bizOwner.setEmail(bizOwnerDto.getEmail());
        bizOwner.setPassword(bizOwnerDto.getPassword());

        BizOwner updated = bizOwnerRepo.save(bizOwner);

        return new BizOwnerDtoReturn(updated.getId(), updated.getName(), updated.getEmail(),updated.getNicNumber());
    }


    @Transactional
    @Override
    public BizOwnerDto deleteBizOwner(Integer id) {
        BizOwner bizOwner = bizOwnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        bizOwnerRepo.deleteById(id);

        return new BizOwnerDto(bizOwner.getId(), bizOwner.getName(), bizOwner.getEmail(),bizOwner.getNicNumber(),bizOwner.getPassword());
    }

    @Override
    public BizOwnerDto getBizOwnerById(Integer id) {
        BizOwner bizOwner = bizOwnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        return new BizOwnerDto(bizOwner.getId(), bizOwner.getName(), bizOwner.getEmail(), bizOwner.getNicNumber(), bizOwner.getPassword());
    }

    @Override
    public List<BizOwnerDtoReturn> getAllBizOwners() {
        return bizOwnerRepo.findAll().stream()
                .map(owner -> new BizOwnerDtoReturn(
                        owner.getId(),
                        owner.getName(),
                        owner.getEmail(),
                        owner.getNicNumber()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public BizOwnerLoginResponse login(BizOwnerLoginRequest request) {

        BizOwner owner = bizOwnerRepo.findByEmail(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), owner.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(owner.getEmail(), "OWNER");

        return new BizOwnerLoginResponse(
                token,
                owner.getId(),
                owner.getName()
        );
    }



}
