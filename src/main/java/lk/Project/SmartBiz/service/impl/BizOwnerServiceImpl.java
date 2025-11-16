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
        if (bizOwnerRepo.findByUsername(bizOwnerDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        String encoded = passwordEncoder.encode(bizOwnerDto.getPassword());

        BizOwner bizOwner = new BizOwner(null, bizOwnerDto.getName(), bizOwnerDto.getUsername(),encoded,null);
        BizOwner save = bizOwnerRepo.save(bizOwner);

        return new BizOwnerDtoReturn(save.getId(), save.getName(), save.getUsername());
    }

    @Override
    public BizOwnerDtoReturn updateBizOwner(BizOwnerDto bizOwnerDto) {
        BizOwner bizOwner = bizOwnerRepo.findById(bizOwnerDto.getId())
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        bizOwner.setName(bizOwnerDto.getName());
        bizOwner.setUsername(bizOwnerDto.getUsername());
        bizOwner.setPassword(bizOwnerDto.getPassword());

        BizOwner updated = bizOwnerRepo.save(bizOwner);

        return new BizOwnerDtoReturn(updated.getId(), updated.getName(), updated.getUsername());
    }


    @Transactional
    @Override
    public BizOwnerDto deleteBizOwner(Integer id) {
        BizOwner bizOwner = bizOwnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        bizOwnerRepo.deleteById(id);

        return new BizOwnerDto(bizOwner.getId(), bizOwner.getName(), bizOwner.getUsername(), bizOwner.getPassword());
    }

    @Override
    public BizOwnerDto getBizOwnerById(Integer id) {
        BizOwner bizOwner = bizOwnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        return new BizOwnerDto(bizOwner.getId(), bizOwner.getName(), bizOwner.getUsername(), bizOwner.getPassword());
    }

    @Override
    public List<BizOwnerDtoReturn> getAllBizOwners() {
        return bizOwnerRepo.findAll().stream()
                .map(owner -> new BizOwnerDtoReturn(
                        owner.getId(),
                        owner.getName(),
                        owner.getUsername()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public BizOwnerLoginResponse login(BizOwnerLoginRequest request) {

        BizOwner owner = bizOwnerRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), owner.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(owner.getUsername(), "OWNER");

        return new BizOwnerLoginResponse(
                token
        );
    }



}
