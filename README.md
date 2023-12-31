# PetStore

<h3>Sobre o projeto</h3>
<p>O aplicativo simula uma loja virtual, uma PetStore, com diversos itens categorizados que podem ser adicionados ao carrinho e têm seu valores somados e exibidos para o usuário. O app utiliza o Retrofit para fazer
a busca das informações da API (um repositório aqui do próprio GitHub), que é uma única lista com todos os produtos juntos. Para dividir a lista em categorias de produtos, utilizei 2 recyclerViews: um para organizar os itens
e outro para separar a lista por categorias. Para salvar os produtos que serão adquiridos, utilizei um banco de dados interno do aparelho com o SQlite</p>
<div align="center">
<img src="https://github.com/phtrebil/PetStore/blob/main/PetStore_Ok.gif" width="300px">
</div>

<h3>Tecnologias utilizadas</h3>

- Android Studio: IDE oficial de desenvolvimento para aplicativos Android.
- Kotlin: Linguagem de programação moderna e oficial para o desenvolvimento Android.
- <a href="https://github.com/phtrebil/PetStore/tree/release/SQlite">SQLite(clique para ir para versão com SQlite):</a> Banco de dados local para armazenar os produtos e informações do carrinho de compras.
- Room(versão main): Banco de dados local para armazenar os produtos e informações do carrinho de compras.
- ViewModel: Componente da arquitetura Jetpack que ajuda a gerenciar e manter os dados da interface do usuário de forma mais organizada, permitindo a separação das preocupações.
- LiveData: Componente da arquitetura Jetpack que oferece uma maneira de observar e reagir a mudanças nos dados de forma reativa.
- RecyclerView: Widget de exibição de lista flexível e eficiente para mostrar listas grandes de itens de forma otimizada.
- Navigation Component: Componente da arquitetura Jetpack que simplifica a navegação entre diferentes partes do aplicativo, mantendo um fluxo de navegação claro e controlado.
- Coil: Biblioteca de carregamento e exibição de imagens de maneira eficiente, facilitando a gestão de imagens no aplicativo.
- AlertDialog: Componente que permite exibir caixas de diálogo modais para interações com o usuário.
- Coroutines: Biblioteca que facilita a programação assíncrona e concorrente em Kotlin, ajudando a evitar bloqueios na thread principal.
- MVVM (Model-View-ViewModel): Padrão de arquitetura que separa os componentes do aplicativo em três camadas principais - Model (representa os dados e a lógica de negócios), View (responsável pela interface do usuário) e ViewModel (gerencia e fornece os dados necessários para a interface do usuário).
- Testes Unitários(jUnit): Os testes unitários com JUnit são usados para testar unidades isoladas de código, como funções ou métodos, de forma independente.
- Testes Instrumentados(Espresso):Os testes instrumentados com Espresso são usados para testar a interação do usuário em aplicativos Android. Eles simulam ações do usuário, como toques na tela, digitação de texto e clique em botões.

<h3>Sobre os Desafios de implementar o projeto</h3>

<p>Gostei muito de poder fazer esse desafio técnico. Fazia um tempinho que não tinha nenhuma ideia nova de projeto para fazer e o desafio surgiu na hora certa. No primeiro momento, minha maior preocupação foi em como organizar as listas: se iria fazer isso pelo código, ou pelo json. Acabei por entender que deveria ser responsabilidade do código separar a categoria de cada produto e montar cada uma das listas. 
  
  <p>Por isso, acabei decidindo fazer um recyclerView aninhado em vez de várias listas, por entender que deixaria mais automatizado e facilitaria a manutenção do código quando fosse necessário adicionar uma nova categoria.
Apesar do esforço, a solução que encontrei - embora funcional - não considero 100% eficiente, uma vez que, a cada nova atualização na categoria dos produtos, será necessário mexer em outros dois pontos do código (os quais estão devidamente comentados).</p>
<p>Para solução da API, acabei utilizando o deploy do próprio github para subir um arquivo Json, assim consegui acessá-lo utilizando o próprio Retrofit e passando os parâmetros reais da página.
Outro grande problema apareceu na hora de implementar a persistência de dados. Normalmente utilizo o Room, que é a ferramenta que estou mais habituado a utilizar. Porém, tive um problema de compatibilidade no meu Android Studio, que parou de aceitar o plugin ‘kotlin-kapt’. A sua ausência estava causando algum conflito com as dependências do Room e eu não soube resolver esse problema. Em outro projeto, utilizei o próprio SQLite para fazer o banco de dados local, portanto foi a solução que utilizei nessa aplicação também. </p>
<p>Assim, o app utiliza o Retrofit para buscar a lista de produtos que pode ser comprada, utiliza recyclerView aninhado para organização das listas, e o banco de dados SQlite para salvar o produtos que o usuário deseja comprar.</p>
<p>Em resumo: gostei muito do desafio e me diverti muito fazendo as implementações! </p>

<hr>

<p><strong>ATUALIZAÇÃO:</strong> Atualmente atualizei o aplicativo e ele está utilizando permanência de dados com o Room, além de ter testes unitários com o jUnit e testes instrumentados com Espresso</p>

<h3>Contribuindo</h3>
<p>Este é um projeto pessoal, mas caso você queira contribuir com alguma melhoria ou correção de bug, sinta-se à vontade para abrir uma issue ou enviar um pull request.</p>
