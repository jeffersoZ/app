1- O ciclo de vida impacta diretamente na performance pois ele vai fazer todo o gerenciamento de memoria do dispositivo indicando quando uma variável é criada o que essa variável armazena e quando você deleta a variável. Em cenário de múltiplas activitys ou rotacionamento de tela se torna ainda mais importante o gerenciamento do ciclo de vida pois nesse cenário precisa ser salvo as informações da tela para passar de uma à outra ou salvar quando muda a posição do telefone. State restorations é o conceito de salvar as informações ao minimizar, alterar de tela ou rodar o celular, para quando que for aberto novamente ainda ter as informações salvar e ser mais performático.


2- Manifest vai ser o xml da aplicação com as configurações crucias dela indicando a linguagem ou em qual tela ele deve abrir.
o res salvará mais informações sobre a parte visual do aplicativo guarda por exemplo os layout themas diferentes background e tudo mais.
Activitys são telas do sistema temos a .xml que seria a parte do layout e temos a .java que guarda a programação e onde pode ser feito o gerenciamento do ciclo de vida 


obs: não consegui mexer com fragmentos e por isso fiz tudo dentro das main activity.