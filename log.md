26/02/2024 - 00:12
==================================================================================================================================================================
# Retrabalho do site [1/3]:
Site retrabalhado no Angular.
Mudança do processador de CSS para SCSS que é o padrão do Angular.
Adição parcial da Anotações financeiras do usuário,sendo elas por enquanto,O serviço de anotações,a parte de criação em annotation-creation, 
e a visualização de todas as anotações(Ainda preciso resolver isso na API, apenas o método de pegar tudo esta funcionando) em annotation-allview.
Adição do Bootstrap ao projeto.
Agora o Token do JWT é armazenado de forma separada no header próprio de autorização, sendo assim agora tem 2 Headers no Local Storage, auth-user e Authorization, um contendo informações gerais do usúario, e agora o novo header contendo a autorização.

#Alteração na API.
Adição de 2 DTOs, sendo AnnotationResponse na classe de payload.response, InputAnnotationRequest na classe payload.request, sendo que o último serve como receptor de resposta vinda do site na parte de criação de anotações, e AnnotationResponse serve como resposta.
Adicionado um analisador de token em AnnotationsRepositories,esse analisador é temporário,
FetchRecent foi alterado para pegar todas as Anotações independete do usúario que a publicou, isso também é temporário.
Os métodos responsáveis por linkar uma anotação ao seu usúario criador foram alterados pois não estavam funcionando direito.
Foi adicionado um filtro simples de CORS(Eu tinha esquecido de criar um quando eu refiz a API).

21/07/2024 - 19:29
=======================================================================================================================================================================

# Alterações no site:

Agora a criação de notas esta funcionando no site,graças a adição de um conversor de timezone.

#Alterações na API.

Adicionado um novo atributo no AnnotationDomain, originalTimeZone que guarda a timezone original do request.

#Docker

Adição de um Dockerfile para criar um container tanto do serviço tanto do banco de dados utilizado.




