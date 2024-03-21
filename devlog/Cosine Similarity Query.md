# Cosine Similarity Query


- 초기 쿼리문

    반복적으로 사용되는 L2 norm을 미리 계산하여 join을 통해 적용하였다.

```
with norms as (
select
    product_id,
    sum(vector_value * vector_value) as squared_value
from vector
group by product_id
), input_norms as (
select
    product_id,
    sum(vector_value * vector_value) as squared_value
from vector
WHERE product_id=10
), input_vector as(
select * from vector WHERE product_id=10
)
select
    x.product_id as product_id,
    sum(x.vector_value * y.vector_value) / sqrt(nx.squared_value * ny.squared_value) as cosine_similarity
from vector as x
join input_vector as y
    on (x.vector_order=y.vector_order)
join norms as nx
    on (nx.product_id=x.product_id)
join input_norms as ny
    on (ny.product_id=y.product_id)
group by x.product_id
order by cosine_similarity desc
```

- 수정후 쿼리문

    with문과 join문을 줄여 단순 명료하게 정의하였다.
```
with input_vector as(
    select * from vector WHERE product_id=5
)
SELECT
A.product_id as pid,
    SUM(A.vector_value * B.vector_value) / (SQRT(SUM(A.vector_value * A.vector_value)) * SQRT(SUM(B.vector_value * B.vector_value))) AS cosine_similarity
FROM vector as A
JOIN input_vector as B
    on (A.vector_order = B.vector_order)
group by A.product_id
order by cosine_similarity desc
```

길이 1000의 Float vector 데이터 100개와 입력된 벡터의 코사인 유사도를 각각 계산
- 수정전: 15002ms 15116ms
- 수정후: 13267ms 13250ms

이전 단계에서 with로 정의한 input_norms와 norms 중 input_norms의 경우 같은 값이 노드끼리의 비교에 반복적으로 사용되기 때문에 효율적일 수 있으나, norms의 경우 어차피 노드가 바뀛 때마다 다시 계산해야하므로 미리 계산해 정의해두는 것이 소용이 없다. 추가적으로 연산해둔 결과를 join을 통해 추가적으로 쿼리를 수행해 오히려 시간이 더 들은 듯 하다.


