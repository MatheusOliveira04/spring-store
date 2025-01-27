INSERT INTO tb_client (id, name, telephone, address_id, date_of_birth, email, cpf)
VALUES ('2d0f0e40-12a5-1d7d-a150-00e0dca0f0a1'::uuid, 'Name test 1', '{123456789, 213456789}',
        '3d6f8e90-29a6-4d9d-a872-62e6dca1f7a7', '2020-07-12', 'testa@testa.com', '12345678912');

INSERT INTO tb_client(id, name, telephone, address_id, date_of_birth, email, cpf)
VALUES ('2d0f0e40-12a5-1d7d-a150-00e0dca0f0a2'::uuid, 'Name test 2', '{423456789, 523456789}',
        'f9a9e0f7-0c8b-42d3-bb65-599d8e2f0a29', '2000-08-12', 'testb@testb.com', '89345678912')
