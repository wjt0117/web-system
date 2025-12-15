package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOption {

    private List jobList;
    private List dataList;
}
