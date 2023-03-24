# SearchesStats
Given a set of dataclasses Search linearly , linearly with indexes , binary with indexes and find the dataclass with the corresponding key.

Firstly the program generates random DataClass[] arrays of different sizes. For each size the program creates pages 
which eventually saves in files. For the linear search without indexes only a data.bin file is needed for the other two searching algorithms 
we need a data.bin file and an indexes.bin file, since we are searching using indexing. After a search is made ( using 1000 distinct keys with range (1,2N + 1) where N 
is the number of DataClass instances, we print the mean time(ns) the average disk accesses and the percentage of keys that were found. It is basically demonstrating the 
power of O(log(N)) instead of O(cN) were c1 > c2 in the first and second searching algorithms.

The source code for the searches is in the src file, while the results (time and accesses) are displayed
in the excel file.
