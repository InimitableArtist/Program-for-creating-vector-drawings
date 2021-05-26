from abc import ABC, abstractmethod

class DocumentModelListener(ABC):

    @abstractmethod
    def documentChanged(self):
        pass